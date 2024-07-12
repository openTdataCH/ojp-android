package ch.opentransportdata.presentation.tir.result

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.presentation.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

/**
 * Created by Michael Ruppen on 02.07.2024
 */
class TripResultViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = MutableStateFlow(UiState())

    val origin: PlaceResultDto? = savedStateHandle["origin"]
    val via: PlaceResultDto? = savedStateHandle["via"]
    val destination: PlaceResultDto? = savedStateHandle["destination"]

    init {
        requestTrips()
    }

    fun loadPreviousTrips() {
        if (state.value.isLoadingPrevious) return
        state.value = state.value.copy(isLoadingPrevious = true)

        val firstTrip = state.value.tripDelivery?.tripResults?.firstOrNull()
        if (firstTrip == null) {
            postEvent(Event.ShowSnackBar("No first trip available"))
            return
        }
        val currentTrips = state.value.tripDelivery?.tripResults?.toMutableList() ?: mutableListOf()


        viewModelScope.launch {
            when (val response = MainActivity.ojpSdk.requestPreviousTrips()) {
                is Result.Success -> {
                    currentTrips.addAll(0, response.data.tripResults)
                    state.value = state.value.copy(
                        tripDelivery = response.data.copy(tripResults = currentTrips),
                        previousItemsLoaded = response.data.tripResults.size
                    )
                    postEvent(Event.ScrollToFirstTripItem)
                }

                is Result.Error -> postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))

            }

        }
    }

    fun loadNextTrips() {
        if (state.value.isLoadingNext) return
        state.value = state.value.copy(isLoadingNext = true)

        val lastTrip = state.value.tripDelivery?.tripResults?.lastOrNull()
        if (lastTrip == null) {
            postEvent(Event.ShowSnackBar("No last trip available"))
            return
        }
        val currentTrips = state.value.tripDelivery?.tripResults?.toMutableList() ?: mutableListOf()

        viewModelScope.launch {
            when (val response = MainActivity.ojpSdk.requestNextTrips()) {
                is Result.Success -> {
                    Log.d("TripResultViewModel", "Fetching next trips successful")
                    currentTrips.addAll(response.data.tripResults)
                    state.value = state.value.copy(tripDelivery = response.data.copy(tripResults = currentTrips))
                }

                is Result.Error -> {
                    Log.e("TripResultViewModel", "Error fetching trip results", response.error.exception)
                    postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))
                }
            }
            state.value = state.value.copy(isLoadingNext = false)

        }
    }

    fun resetTripState() {
        MainActivity.ojpSdk.resetTripState()
    }

    fun resetPreviousItemsCounter() {
        state.value = state.value.copy(previousItemsLoaded = 0, isLoadingPrevious = false)
    }

    fun eventHandled(id: Long) {
        val events = state.value.events.filterNot { it.id == id }
        state.value = state.value.copy(events = events)
    }

    private fun requestTrips() {
        if (origin == null || destination == null) {
            Log.e("TripResultViewModel", "Origin or destination are null")
            return
        }

        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val response = MainActivity.ojpSdk.requestTrips(
                origin = origin,
                destination = destination,
                via = via,
                time = LocalDateTime.now(),
                params = TripParamsDto(numberOfResults = 10)
            )
            when (response) {
                is Result.Success -> {
                    Log.d("TripResultViewModel", "Fetching trip was successful")
                    state.value = state.value.copy(tripDelivery = response.data)
                    postEvent(Event.ScrollToFirstTripItem)
                }

                is Result.Error -> {
                    Log.e("TripResultViewModel", "Error fetching trip results", response.error.exception)
                    state.value = state.value.copy(tripDelivery = null)
                    postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))
                }
            }
            state.value = state.value.copy(isLoading = false)

        }
    }

    private fun postEvent(event: Event) {
        val events = state.value.events + event
        state.value = state.value.copy(events = events)
    }

    sealed class Event(val id: Long = UUID.randomUUID().mostSignificantBits) {
        data class ShowSnackBar(val message: String) : Event()
        data object ScrollToFirstTripItem : Event()
    }

    @Immutable
    data class UiState(
        val tripDelivery: TripDeliveryDto? = null,
        val events: List<Event> = emptyList(),
        val isLoadingPrevious: Boolean = false,
        val isLoading: Boolean = false,
        val isLoadingNext: Boolean = false,
        val previousItemsLoaded: Int = 0,

        )
}