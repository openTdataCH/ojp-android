package ch.opentransportdata.presentation.lir

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.opentransportdata.data.DefaultLocationTracker
import ch.opentransportdata.ojp.OjpSdk
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.Response
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Created by Michael Ruppen on 25.04.2024
 */
class LirViewModel : ViewModel() {

    private lateinit var locationTracker: DefaultLocationTracker

    val state = MutableStateFlow(UiState())

    private val ojpSdk = OjpSdk(
        baseUrl = "https://odpch-api.clients.liip.ch/",
        endpoint = "ojp20-beta",
        httpHeaders = hashMapOf(
            Pair(
                "Authorization",
                "Bearer eyJvcmciOiI2M2Q4ODhiMDNmZmRmODAwMDEzMDIwODkiLCJpZCI6IjUzYzAyNWI2ZTRhNjQyOTM4NzMxMDRjNTg2ODEzNTYyIiwiaCI6Im11cm11cjEyOCJ9"
            )
        ),
        requesterReference = "DemoApp",
    )

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
                    postEvent(Event.ShowSnackBar(message = "An error has occurred"))
                }

                else -> {
                    when (val result = ojpSdk.requestLocationsFromCoordinates(
                        longitude = currentLocation.longitude, latitude = currentLocation.latitude, onlyStation = true
                    )) {
                        is Response.Success -> state.value = state.value.copy(results = result.data.map { it })

                        is Response.Error -> {
                            state.value = state.value.copy(results = emptyList())
                            postEvent(Event.ShowSnackBar(message = "An error has occurred"))
                            Log.e(TAG, "Error fetching data: ${result.error}")
                        }
                    }
                }
            }

            state.value = state.value.copy(isLoading = false)
        }
    }

    fun fetchLocations(input: String) {
        state.value = state.value.copy(inputValue = input)
        viewModelScope.launch {
            when (val result = ojpSdk.requestLocationsFromSearchTerm(term = input, onlyStation = true)) {
                is Response.Success -> state.value = state.value.copy(results = result.data)
                is Response.Error -> Log.e(TAG, "Error fetching data: ${result.error}")
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
