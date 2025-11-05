package ch.opentransportdata.presentation.tir.result

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.ContinuousLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TimedLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.domain.MapLibreData
import ch.opentransportdata.ojp.domain.model.ModeAndModeOfOperationFilter
import ch.opentransportdata.ojp.domain.model.PtMode
import ch.opentransportdata.ojp.domain.model.RealtimeData
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripParams
import ch.opentransportdata.ojp.domain.model.TripRefineParam
import ch.opentransportdata.presentation.MainActivity
import ch.opentransportdata.presentation.utils.toOjpLanguageCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream
import java.time.LocalDateTime
import java.util.Locale
import java.util.UUID

/**
 * Created by Michael Ruppen on 02.07.2024
 */
class TripResultViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = MutableStateFlow(UiState())

    val origin: PlaceResultDto?
        get() = savedStateHandle["origin"]
    val via: PlaceResultDto?
        get() = savedStateHandle["via"]
    val destination: PlaceResultDto?
        get() = savedStateHandle["destination"]

    init {
        requestTrips()
    }

    fun loadPreviousTrips() {
        if (state.value.isLoadingPrevious) return
        state.update { it.copy(isLoadingPrevious = true) }

        val firstTrip = state.value.tripDelivery?.tripResults?.firstOrNull()
        if (firstTrip == null) {
            postEvent(Event.ShowSnackBar("No first trip available"))
            state.update { it.copy(isLoadingPrevious = false) }
            return
        }
        val currentTrips = state.value.tripDelivery?.tripResults?.toMutableList() ?: mutableListOf()

        viewModelScope.launch {
            when (val response = MainActivity.ojpSdk.requestPreviousTrips()) {
                is Result.Success -> {
                    response.data.tripResults?.let { tripResults ->
                        currentTrips.addAll(0, tripResults)
                        state.update { it.copy(tripDelivery = response.data.copy(tripResults = currentTrips)) }
                        postEvent(Event.ScrollToFirstTripItem(tripResults.size))
                    }
                }

                is Result.Error -> postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))
            }
        }
    }

    fun loadNextTrips() {
        if (state.value.isLoadingNext) return
        state.update { it.copy(isLoadingNext = true) }

        val lastTrip = state.value.tripDelivery?.tripResults?.lastOrNull()
        if (lastTrip == null) {
            postEvent(Event.ShowSnackBar("No last trip available"))
            state.update { it.copy(isLoadingNext = false) }
            return
        }
        val currentTrips = state.value.tripDelivery?.tripResults?.toMutableList() ?: mutableListOf()

        viewModelScope.launch {
            when (val response = MainActivity.ojpSdk.requestNextTrips()) {
                is Result.Success -> {
                    Log.d("TripResultViewModel", "Fetching next trips successful")
                    response.data.tripResults?.let { currentTrips.addAll(it) }
                    state.update { it.copy(tripDelivery = response.data.copy(tripResults = currentTrips)) }
                }

                is Result.Error -> {
                    Log.e("TripResultViewModel", "Error fetching trip results", response.error.exception)
                    postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))
                }
            }
            state.update { it.copy(isLoadingNext = false) }
        }
    }

    fun swapSearch() {
        val currentOrigin = origin
        savedStateHandle["origin"] = destination
        savedStateHandle["destination"] = currentOrigin
        requestTrips()
    }

    fun resetPreviousItemsCounter() {
        state.update { it.copy(isLoadingPrevious = false) }
    }

    fun requestMockTrips(source: InputStream) {
        viewModelScope.launch {
            when (val response = MainActivity.ojpSdk.requestMockTrips(source)) {
                is Result.Success -> {
                    Log.d("TripResultViewModel", "Mock data successfully parsed")
                    state.update { it.copy(tripDelivery = response.data.copy(tripResults = response.data.tripResults)) }
                }

                is Result.Error -> {
                    Log.e("TripResultViewModel", "Error parsing mock file", response.error.exception)
                    postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))
                }
            }
        }
    }

    fun requestTripUpdate(trip: TripDto) {
        val originRef = PlaceReferenceDto(
            ref = (origin!!.place?.placeType as? StopPlaceDto)?.stopPlaceRef,
            stationName = (origin!!.place?.placeType as? StopPlaceDto)?.name,
            position = origin!!.place?.position
        )
        val destinationRef = PlaceReferenceDto(
            ref = (destination!!.place?.placeType as? StopPlaceDto)?.stopPlaceRef,
            stationName = (destination!!.place?.placeType as? StopPlaceDto)?.name,
            position = destination!!.place?.position
        )
        val viaRef = via?.let {
            PlaceReferenceDto(
                ref = (it.place?.placeType as? StopPlaceDto)?.stopPlaceRef,
                stationName = (it.place?.placeType as? StopPlaceDto)?.name,
                position = it.place?.position
            )
        }

        viewModelScope.launch {
            when (val response = MainActivity.ojpSdk.updateTripData(
                languageCode = LanguageCode.EN,
                origin = originRef,
                destination = destinationRef,
                via = viaRef,
                params = TripParams(
                    numberOfResults = 10,
                    includeIntermediateStops = true,
                    includeAllRestrictedLines = true,
                    modeAndModeOfOperationFilter = null,
                    useRealtimeData = RealtimeData.EXPLANATORY
                ),
                trip = trip,
            )) {
                is Result.Success -> {
                    Log.d("TripResultViewModel", "Trip update successful")
//                    state.update { it.copy(tripDelivery = response.data.copy(tripResults = response.data.tripResults)) }
                }

                is Result.Error -> {
                    Log.e("TripResultViewModel", "Error updating trip", response.error.exception)
                    postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))
                }
            }
        }
    }

    fun eventHandled(id: Long) {
        state.update { it.copy(events = it.events.filterNot { event -> event.id == id }) }
    }

    private fun requestTrips() {
        if (origin == null || destination == null) {
            Log.e("TripResultViewModel", "Origin or destination are null")
            return
        }

        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            val originRef = PlaceReferenceDto(
                ref = (origin!!.place?.placeType as? StopPlaceDto)?.stopPlaceRef,
                stationName = (origin!!.place?.placeType as? StopPlaceDto)?.name,
                position = origin!!.place?.position
            )
            val destinationRef = PlaceReferenceDto(
                ref = (destination!!.place?.placeType as? StopPlaceDto)?.stopPlaceRef,
                stationName = (destination!!.place?.placeType as? StopPlaceDto)?.name,
                position = destination!!.place?.position
            )
            val viaRef = via?.let {
                PlaceReferenceDto(
                    ref = (it.place?.placeType as? StopPlaceDto)?.stopPlaceRef,
                    stationName = (it.place?.placeType as? StopPlaceDto)?.name,
                    position = it.place?.position
                )
            }
            //example if you want only water trips when both stations are water
            val modeFilter =
                if (origin!!.place?.mode?.any { it.ptMode == PtMode.WATER } == true && destination!!.place?.mode?.any { it.ptMode == PtMode.WATER } == true) {
                    listOf(PtMode.WATER)
                } else emptyList()

            val modeAndModeOfOperationFilter = if (modeFilter.isNotEmpty()) {
                listOf(
                    ModeAndModeOfOperationFilter(
                        ptMode = modeFilter,
                        exclude = false
                    )
                )
            } else emptyList()

            val response = MainActivity.ojpSdk.requestTrips(
                languageCode = Locale.getDefault().language.toOjpLanguageCode(),
                origin = originRef,
                destination = destinationRef,
                via = viaRef,
                time = LocalDateTime.now(),
                params = TripParams(
                    numberOfResults = 10,
                    includeIntermediateStops = true,
                    includeAllRestrictedLines = true,
                    modeAndModeOfOperationFilter = modeAndModeOfOperationFilter,
                    useRealtimeData = RealtimeData.EXPLANATORY
                )
            )
            when (response) {
                is Result.Success -> {
                    Log.d("TripResultViewModel", "Fetching trip was successful")
                    state.update { it.copy(tripDelivery = response.data) }
                    postEvent(Event.ScrollToFirstTripItem(0))
                }

                is Result.Error -> {
                    Log.e("TripResultViewModel", "Error fetching trip results", response.error.exception)
                    state.update { it.copy(tripDelivery = null) }
                    postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))
                }
            }
            state.update { it.copy(isLoading = false) }
        }
    }

    private fun postEvent(event: Event) {
        state.update { it.copy(events = it.events + event) }
    }

    fun refineTrip(id: String) {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            val tripResult = state.value.tripDelivery?.tripResults?.find { it.id == id }
            tripResult?.let {
                val response = MainActivity.ojpSdk.requestTripRefinement(
                    languageCode = Locale.getDefault().language.toOjpLanguageCode(),
                    tripResult = tripResult,
                    params = TripRefineParam(
                        includeIntermediateStops = true,
                        includeAllRestrictedLines = true,
                        includeTurnDescription = true,
                        includeLegProjection = true,
                        includeTrackSections = true,
                        useRealtimeData = RealtimeData.FULL
                    )
                )
                when (response) {
                    is Result.Success -> {
                        Log.d("TripResultViewModel", "Refinement was successful ${response.data}")
                        val mapData = mutableListOf<MapLibreData>()
                        response.data.tripResults?.forEach { result ->
                            result.trip?.legs?.forEach { leg ->
                                when(leg.legType) {
                                    is ContinuousLegDto -> {
                                        val positions = leg.continuousLeg?.legTrack?.trackSection?.first()?.linkProjection?.positions
                                        if (!positions.isNullOrEmpty()) {
                                            mapData.add(MapLibreData(
                                                id = leg.id,
                                                positions = positions
                                            ))
                                        }
                                    }
                                    is TransferLegDto -> {
                                        val positions = leg.transferLeg?.pathGuidance?.pathGuidanceSection?.first()?.trackSection?.first()?.linkProjection?.positions
                                        if (!positions.isNullOrEmpty()) {
                                            mapData.add(MapLibreData(
                                                id = leg.id,
                                                positions = positions
                                            ))
                                        }
                                    }
                                    is TimedLegDto -> {
                                        val positions = leg.timedLeg?.legTrack?.trackSection?.first()?.linkProjection?.positions
                                        if (!positions.isNullOrEmpty()) {
                                            mapData.add(MapLibreData(
                                                id = leg.id,
                                                positions = positions
                                            ))
                                        }
                                    }
                                }
                            }
                            state.update { it.copy(mapData = mapData) }
                        }
                        postEvent(Event.ScrollToFirstTripItem(0))
                    }

                    is Result.Error -> {
                        Log.e("TripResultViewModel", "Error fetching trip results" + response.error.exception)
                        postEvent(Event.ShowSnackBar("Error: ${response.error.exception.message}"))
                    }
                }
            }
            state.update { it.copy(isLoading = false) }
        }
    }

    fun resetMapData() {
        state.update { it.copy(mapData = emptyList()) }
    }

    sealed class Event(val id: Long = UUID.randomUUID().mostSignificantBits) {
        data class ShowSnackBar(val message: String) : Event()
        data class ScrollToFirstTripItem(val offset: Int) : Event()
    }

    @Immutable
    data class UiState(
        val tripDelivery: TripDeliveryDto? = null,
        val events: List<Event> = emptyList(),
        val isLoadingPrevious: Boolean = false,
        val isLoading: Boolean = false,
        val isLoadingNext: Boolean = false,
        val mapData: List<MapLibreData> = emptyList()
    )
}