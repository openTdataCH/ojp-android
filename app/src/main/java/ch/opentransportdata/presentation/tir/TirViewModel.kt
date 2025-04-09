package ch.opentransportdata.presentation.tir

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
import ch.opentransportdata.presentation.lir.name
import ch.opentransportdata.presentation.utils.toOjpLanguageCode
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import java.util.UUID

/**
 * Created by Michael Ruppen on 25.04.2024
 */
class TirViewModel : ViewModel() {

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

    fun getCurrentLocation(isOrigin: Boolean, isDestination: Boolean) {
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
                        is Result.Success -> {
                            if (result.data.isEmpty()) {
                                postEvent(Event.ShowSnackBar(message = "An error has occurred"))
                            } else {
                                val location = result.data.first()
                                val input = TextInput(
                                    hasFocus = false,
                                    textInputValue = "Current Location",
                                    selectedLocation = location
                                )
                                state.value = state.value.copy(
                                    results = emptyList(),
                                    origin = if (isOrigin) input else state.value.origin,
                                    destination = if (isDestination) input else state.value.destination
                                )
                            }
                        }

                        is Result.Error -> {
                            Log.e(TAG, "Error fetching data", result.error.exception)
                            state.value = state.value.copy(results = emptyList())
                            postEvent(Event.ShowSnackBar(message = "An error has occurred"))
                        }
                    }
                }
            }

            state.value = state.value.copy(isLoading = false)
            checkFormValidity()
        }
    }

    fun fetchLocations(
        input: String,
        isOrigin: Boolean = false,
        isVia: Boolean = false,
        isDestination: Boolean = false
    ) {
        when {
            isOrigin -> state.value = state.value.copy(origin = state.value.origin.copy(textInputValue = input))
            isVia -> state.value = state.value.copy(via = state.value.via.copy(textInputValue = input))
            isDestination -> state.value = state.value.copy(destination = state.value.destination.copy(textInputValue = input))
        }
        val placeTypeRestriction = when (isVia) {
            true -> listOf(PlaceTypeRestriction.STOP)
            else -> listOf(PlaceTypeRestriction.STOP, PlaceTypeRestriction.ADDRESS)
        }

        searchScope.coroutineContext.cancelChildren()
        searchScope.launch {
            when (val result = MainActivity.ojpSdk.requestLocationsFromSearchTerm(
                languageCode = Locale.getDefault().language.toOjpLanguageCode(),
                term = input,
                restrictions = LocationInformationParams(
                    types = placeTypeRestriction,
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

    fun updateFocus(updateOriginFocus: Boolean, updateViaFocus: Boolean, updateDestinationFocus: Boolean) {
        val originFocus = if (updateOriginFocus) !state.value.origin.hasFocus else state.value.origin.hasFocus
        val viaFocus = if (updateViaFocus) !state.value.via.hasFocus else state.value.via.hasFocus
        val destinationFocus = if (updateDestinationFocus) !state.value.destination.hasFocus else state.value.destination.hasFocus

        state.value = state.value.copy(
            origin = state.value.origin.copy(hasFocus = if (updateOriginFocus) originFocus else false),
            via = state.value.via.copy(hasFocus = if (updateViaFocus) viaFocus else false),
            destination = state.value.destination.copy(hasFocus = if (updateDestinationFocus) destinationFocus else false),
        )
    }

    fun onLocationSelected(location: PlaceResultDto) {
        state.value = when {
            state.value.origin.hasFocus -> state.value.copy(
                origin = state.value.origin.copy(
                    selectedLocation = location,
                    textInputValue = location.place?.placeType?.name() ?: "undef"
                ),
                results = emptyList()
            )

            state.value.via.hasFocus -> state.value.copy(
                via = state.value.via.copy(selectedLocation = location),
                results = emptyList()
            )

            state.value.destination.hasFocus -> state.value.copy(
                destination = state.value.destination.copy(selectedLocation = location),
                results = emptyList()
            )

            else -> state.value
        }

        checkFormValidity()
    }

    fun resetData() {
        state.value = UiState()
    }

    fun eventHandled(id: Long) {
        val events = state.value.events.filterNot { it.id == id }
        state.value = state.value.copy(events = events)
    }

    private fun checkFormValidity() {
        if (state.value.origin.selectedLocation != null && state.value.destination.selectedLocation != null) {
            postEvent(
                Event.RequestTrip(
                    state.value.origin.selectedLocation!!,
                    state.value.via.selectedLocation,
                    state.value.destination.selectedLocation!!
                )
            )
        }
    }

    private fun postEvent(event: Event) {
        val events = state.value.events + event
        state.value = state.value.copy(events = events)
    }

    sealed class Event(val id: Long = UUID.randomUUID().mostSignificantBits) {
        data class ShowSnackBar(val message: String) : Event()
        data class RequestTrip(val origin: PlaceResultDto, val via: PlaceResultDto?, val destination: PlaceResultDto) : Event()
    }

    @Immutable
    data class UiState(
        val origin: TextInput = TextInput(hasFocus = true),
        val via: TextInput = TextInput(),
        val destination: TextInput = TextInput(),
        val isLoading: Boolean = false,
        val results: List<PlaceResultDto> = emptyList(),
        val events: List<Event> = emptyList()
    )

    @Immutable
    data class TextInput(
        val hasFocus: Boolean = false,
        val textInputValue: String = "",
        val selectedLocation: PlaceResultDto? = null
    )

//    sealed class TextInput(
//        open val hasFocus: Boolean,
//        open val textInputValue: String,
//        open val selectedLocation: String?
//    ) {
//        data class Origin(
//            override val hasFocus: Boolean = false,
//            override val textInputValue: String = "",
//            override val selectedLocation: String? = null
//        ) : TextInput(hasFocus, textInputValue, selectedLocation)
//
//        data class Via(
//            override val hasFocus: Boolean = false,
//            override val textInputValue: String = "",
//            override val selectedLocation: String? = null
//        ) : TextInput(hasFocus, textInputValue, selectedLocation)
//
//        data class Destination(
//            override val hasFocus: Boolean = false,
//            override val textInputValue: String = "",
//            override val selectedLocation: String? = null
//        ) : TextInput(hasFocus, textInputValue, selectedLocation)
//    }

    companion object {
        private const val TAG = "LirViewModel"
    }

}
