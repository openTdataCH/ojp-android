package ch.opentransportdata.presentation.feature.stopevent

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.opentransportdata.ojp.data.dto.request.ser.LocationDto
import ch.opentransportdata.ojp.data.dto.request.tr.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.ser.StopEventResultDto
import ch.opentransportdata.ojp.domain.model.RealtimeData
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.StopEventParam
import ch.opentransportdata.ojp.domain.model.StopEventType
import ch.opentransportdata.presentation.MainActivity
import ch.opentransportdata.presentation.util.toOjpLanguageCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Locale
import java.util.UUID

/**
 * Created by Deniz Kalem on 20.05.2026
 */
class StopEventResultViewModel : ViewModel() {

    val state = MutableStateFlow(UiState())

    fun load(stop: PlaceResultDto, stopEventType: StopEventType) {
        val ref = stop.place?.stopPlace?.stopPlaceRef
        if (ref == null) {
            postEvent(Event.ShowSnackBar("Selected place has no stop place reference"))
            return
        }

        state.value = state.value.copy(
            isLoading = true,
            stopName = stop.place?.stopPlace?.name?.text,
            stopEventType = stopEventType,
        )

        viewModelScope.launch(Dispatchers.IO) {
            val result = MainActivity.ojpSdk.requestStopEvent(
                languageCode = Locale.getDefault().language.toOjpLanguageCode(),
                location = LocationDto(
                    placeReference = PlaceReferenceDto(ref = ref),
                    depArrTime = LocalDateTime.now(),
                ),
                params = StopEventParam(
                    numberOfResults = 20,
                    stopEventType = stopEventType,
                    includeOnwardCalls = true,
                    includeOperatingDays = false,
                    useRealtimeData = RealtimeData.FULL,
                )
            )

            when (result) {
                is Result.Success -> state.value = state.value.copy(
                    results = result.data.stopEventResults.orEmpty(),
                    isLoading = false,
                )

                is Result.Error -> {
                    Log.e(TAG, "Error fetching stop events", result.error.exception)
                    state.value = state.value.copy(results = emptyList(), isLoading = false)
                    postEvent(Event.ShowSnackBar("Failed to load stop events"))
                }
            }
        }
    }

    fun toggleStopEventType(stop: PlaceResultDto) {
        val next = if (state.value.stopEventType == StopEventType.DEPARTURE) {
            StopEventType.ARRIVAL
        } else {
            StopEventType.DEPARTURE
        }
        load(stop, next)
    }

    fun eventHandled(id: Long) {
        state.value = state.value.copy(events = state.value.events.filterNot { it.id == id })
    }

    private fun postEvent(event: Event) {
        state.value = state.value.copy(events = state.value.events + event)
    }

    sealed class Event(val id: Long = UUID.randomUUID().mostSignificantBits) {
        data class ShowSnackBar(val message: String) : Event()
    }

    @Immutable
    data class UiState(
        val isLoading: Boolean = false,
        val stopName: String? = null,
        val stopEventType: StopEventType = StopEventType.DEPARTURE,
        val results: List<StopEventResultDto> = emptyList(),
        val events: List<Event> = emptyList(),
    )

    companion object {
        private const val TAG = "StopEventResultViewModel"
    }
}