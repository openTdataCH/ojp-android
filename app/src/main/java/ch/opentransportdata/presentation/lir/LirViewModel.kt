package ch.opentransportdata.presentation.lir

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.opentransportdata.data.DefaultLocationTracker
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.presentation.MainActivity
import ch.opentransportdata.presentation.utils.toOjpLanguageCode
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import java.util.UUID

/**
 * Created by Michael Ruppen on 25.04.2024
 */
class LirViewModel : ViewModel() {

    private lateinit var locationTracker: DefaultLocationTracker

    val state = MutableStateFlow(UiState())

    private val job = SupervisorJob()
    private val searchScope = CoroutineScope(job + Dispatchers.IO)

    fun initLocationTracker(context: Context) {
        locationTracker = DefaultLocationTracker(
            LocationServices.getFusedLocationProviderClient(context),
            context
        )
    }

    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = state.value.copy(isLoading = true)
            var currentLocation: Location? = null
            var attempt = 0
            while (currentLocation == null && attempt < 5) {
                currentLocation = locationTracker.getCurrentLocation()
                Log.d(TAG, "Current lon: ${currentLocation?.longitude}, lat: ${currentLocation?.latitude}")
                attempt++
                delay(1000)
            }

            when (currentLocation == null) {
                true -> {
                    state.value = state.value.copy(results = emptyList())
                    postEvent(Event.ShowSnackBar(message = "No location received"))
                }

                else -> {
                    when (val result = MainActivity.ojpSdk.requestLocationsFromCoordinates(
                        languageCode = Locale.getDefault().language.toOjpLanguageCode(),
                        longitude = currentLocation.longitude,
                        latitude = currentLocation.latitude,
                        restrictions = LocationInformationParams(
                            types = listOf(PlaceTypeRestriction.STOP),
                            numberOfResults = 10,
                            ptModeIncluded = true
                        )
                    )) {
                        is Result.Success -> state.value = state.value.copy(results = result.data.map { it })

                        is Result.Error -> {
                            Log.e(TAG, "Error fetching data", result.error.exception)
                            state.value = state.value.copy(results = emptyList())
                            postEvent(Event.ShowSnackBar(message = "An error has occurred"))
                        }
                    }
                }
            }

            state.value = state.value.copy(isLoading = false)
        }
    }

    fun fetchLocations(input: String) {
        state.value = state.value.copy(inputValue = input)
        searchScope.coroutineContext.cancelChildren()
        searchScope.launch {
            when (val result = MainActivity.ojpSdk.requestLocationsFromSearchTerm(
                languageCode = Locale.getDefault().language.toOjpLanguageCode(),
                term = input,
                restrictions = LocationInformationParams(
                    types = listOf(PlaceTypeRestriction.STOP, PlaceTypeRestriction.ADDRESS),
                    numberOfResults = 10,
                    ptModeIncluded = true
                )
            )
            ) {
                is Result.Success -> state.value = state.value.copy(results = result.data)
                is Result.Error -> {
                    Log.e(TAG, "Error fetching data", result.error.exception)
                    if (result.error !is OjpError.RequestCancelled) postEvent(Event.ShowSnackBar("Something went wrong"))
                }
            }
        }
    }

    fun eventHandled(id: Long) {
        val events = state.value.events.filterNot { it.id == id }
        state.value = state.value.copy(events = events)
    }

    private fun postEvent(event: Event) {
        val events = state.value.events + event
        state.value = state.value.copy(events = events)
    }

    sealed class Event(val id: Long = UUID.randomUUID().mostSignificantBits) {
        data class ShowSnackBar(val message: String) : Event()
    }

    @Immutable
    data class UiState(
        val inputValue: String = "",
        val isLoading: Boolean = false,
        val results: List<PlaceResultDto> = emptyList(),
        val events: List<Event> = emptyList()
    )

    companion object {
        private const val TAG = "LirViewModel"
    }

}
