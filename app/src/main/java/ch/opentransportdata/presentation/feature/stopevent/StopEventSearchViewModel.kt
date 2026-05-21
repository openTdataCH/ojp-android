package ch.opentransportdata.presentation.feature.stopevent

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.presentation.MainActivity
import ch.opentransportdata.presentation.util.toOjpLanguageCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

/**
 * Created by Deniz Kalem on 20.05.2026
 */
class StopEventSearchViewModel : ViewModel() {

    val state = MutableStateFlow(UiState())

    private val job = SupervisorJob()
    private val searchScope = CoroutineScope(job + Dispatchers.IO)

    fun fetchLocations(input: String) {
        state.value = state.value.copy(inputValue = input)
        if (input.isBlank()) {
            state.value = state.value.copy(results = emptyList())
            return
        }

        searchScope.coroutineContext.cancelChildren()
        searchScope.launch {
            when (val result = MainActivity.ojpSdk.requestLocationsFromSearchTerm(
                languageCode = Locale.getDefault().language.toOjpLanguageCode(),
                term = input,
                restrictions = LocationInformationParams(
                    types = listOf(PlaceTypeRestriction.STOP),
                    numberOfResults = 10,
                    ptModeIncluded = true
                )
            )) {
                is Result.Success -> state.value = state.value.copy(results = result.data)
                is Result.Error -> {
                    Log.e(TAG, "Error fetching data", result.error.exception)
                    if (result.error !is OjpError.RequestCancelled) postEvent(Event.ShowSnackBar("Something went wrong"))
                }
            }
        }
    }

    fun onLocationSelected(location: PlaceResultDto) {
        postEvent(Event.OpenResults(location))
    }

    fun eventHandled(id: Long) {
        val events = state.value.events.filterNot { it.id == id }
        state.value = state.value.copy(events = events)
    }

    fun resetData() {
        state.value = UiState()
    }

    private fun postEvent(event: Event) {
        state.value = state.value.copy(events = state.value.events + event)
    }

    sealed class Event(val id: Long = UUID.randomUUID().mostSignificantBits) {
        data class ShowSnackBar(val message: String) : Event()
        data class OpenResults(val location: PlaceResultDto) : Event()
    }

    @Immutable
    data class UiState(
        val inputValue: String = "",
        val results: List<PlaceResultDto> = emptyList(),
        val events: List<Event> = emptyList(),
    )

    companion object {
        private const val TAG = "StopEventSearchViewModel"
    }
}