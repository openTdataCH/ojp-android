package ch.opentransportdata.presentation.tir.result

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.opentransportdata.domain.MapLibreData
import ch.opentransportdata.domain.VehicleOption
import ch.opentransportdata.ojp.data.dto.request.tir.IndividualTransportOptionDto
import ch.opentransportdata.ojp.data.dto.request.tir.ItModeAndModeOfOperationDto
import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.ContinuousLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TimedLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.ModeAndModeOfOperationFilter
import ch.opentransportdata.ojp.domain.model.OptimisationMethod
import ch.opentransportdata.ojp.domain.model.PtMode
import ch.opentransportdata.ojp.domain.model.RailSubmode
import ch.opentransportdata.ojp.domain.model.RealtimeData
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripParams
import ch.opentransportdata.ojp.domain.model.TripRefineParam
import ch.opentransportdata.ojp.domain.model.serializedName
import ch.opentransportdata.presentation.MainActivity
import ch.opentransportdata.presentation.utils.toOjpLanguageCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream
import java.time.Duration
import java.time.LocalDateTime
import java.util.Locale
import java.util.UUID

/**
 * Created by Michael Ruppen on 02.07.2024
 */
class TripResultViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialVehicleOptions = listOf(
        "Train", "Bus", "Tram", "Metro", "Boat", "Telecabin"
    ).map { vehicleType ->
        VehicleOption(vehicleType = vehicleType, isSelected = true)
    }

    private val initialVehicleSubOptions = listOf(
        RailSubmode.INTERNATIONAL.serializedName(),
        RailSubmode.HIGH_SPEED_RAIL.serializedName(),
        RailSubmode.INTERREGIONAL_RAIL.serializedName(),
        RailSubmode.RAIL_SHUTTLE.serializedName(),
        RailSubmode.LOCAL.serializedName()
    ).map { vehicleType ->
        VehicleOption(vehicleType = vehicleType, isSelected = false)
    }


    val state = MutableStateFlow(
        UiState(
            vehicleOptions = initialVehicleOptions,
            vehicleSubOptions = initialVehicleSubOptions
        )
    )

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
                    useRealtimeData = RealtimeData.EXPLANATORY,
                    walkSpeed = state.value.walkingSpeed,
                    transferLimit = if (state.value.isDirectConnection) 0 else null,
                    optimisationMethod = if (state.value.isFewerTransfers) OptimisationMethod.MIN_CHANGES else null,
                    bikeTransport = state.value.isBikeTransport,
                ),
                trip = trip,
                individualTransportOption = IndividualTransportOptionDto(
                    minDistance = state.value.minDistance,
                    maxDistance = state.value.maxDistance,
                    minDuration = state.value.minDuration?.let { Duration.ofMinutes(it) },
                    maxDuration = state.value.maxDuration?.let { Duration.ofMinutes(it) },
                )
            )) {
                is Result.Success -> {
                    Log.d("TripResultViewModel", "Trip update successful")
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

    fun requestTrips() {
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

            val selectedPtModes: List<PtMode> = buildList {
                if (state.value.vehicleOptions.first().isSelected) {
                    add(PtMode.RAIL)
                    add(PtMode.SUBURBAN_RAIL)
                    add(PtMode.URBAN_RAIL)
                }
                if (state.value.vehicleOptions[1].isSelected) add(PtMode.BUS)
                if (state.value.vehicleOptions[2].isSelected) add(PtMode.TRAM)
                if (state.value.vehicleOptions[3].isSelected) add(PtMode.METRO)
                if (state.value.vehicleOptions[4].isSelected) add(PtMode.WATER)
                if (state.value.vehicleOptions.last().isSelected) add(PtMode.TELECABIN)

                if (isEmpty()) add(PtMode.ALL)
            }

            val selectedPtSubModes: MutableList<ModeAndModeOfOperationFilter> =
                if (PtMode.RAIL in selectedPtModes) {
                    state.value.vehicleSubOptions
                        .filter { it.isSelected }
                        .map { vehicleOption ->
                            ModeAndModeOfOperationFilter(
                                exclude = false,
                                ptMode = emptyList(),
                                railSubmode = RailSubmode.entries.firstOrNull { it.serializedName() == vehicleOption.vehicleType }
                            )
                        }
                        .toMutableList()
                } else {
                    mutableListOf()
                }

            val railModes = setOf(PtMode.RAIL, PtMode.SUBURBAN_RAIL, PtMode.URBAN_RAIL)

            val ptModesForMainFilter =
                if (selectedPtSubModes.isNotEmpty()) selectedPtModes.filterNot { it in railModes }
                else selectedPtModes

            val modeAndModeOfOperationFilter: List<ModeAndModeOfOperationFilter> = buildList {
                add(ModeAndModeOfOperationFilter(ptMode = ptModesForMainFilter, exclude = false))
                addAll(selectedPtSubModes)
            }

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
                    useRealtimeData = RealtimeData.EXPLANATORY,
                    walkSpeed = state.value.walkingSpeed,
                    transferLimit = if (state.value.isDirectConnection) 0 else null,
                    optimisationMethod = if (state.value.isFewerTransfers) OptimisationMethod.MIN_CHANGES else null,
                    bikeTransport = state.value.isBikeTransport,
                ),
                individualTransportOption = IndividualTransportOptionDto(
                    itModeAndModeOfOperation = ItModeAndModeOfOperationDto(
                        personalMode = "foot",
                        personalModeOfOperation = "own"
                    ),
                    minDistance = state.value.minDistance,
                    maxDistance = state.value.maxDistance,
                    minDuration = state.value.minDuration?.let { Duration.ofMinutes(it) },
                    maxDuration = state.value.maxDuration?.let { Duration.ofMinutes(it) },
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
                                when (leg.legType) {
                                    is ContinuousLegDto -> {
                                        val positions =
                                            leg.continuousLeg?.legTrack?.trackSection?.first()?.linkProjection?.positions
                                        if (!positions.isNullOrEmpty()) {
                                            mapData.add(
                                                MapLibreData(
                                                    id = leg.id,
                                                    positions = positions
                                                )
                                            )
                                        }
                                    }

                                    is TransferLegDto -> {
                                        val positions =
                                            leg.transferLeg?.pathGuidance?.pathGuidanceSection?.first()?.trackSection?.first()?.linkProjection?.positions
                                        if (!positions.isNullOrEmpty()) {
                                            mapData.add(
                                                MapLibreData(
                                                    id = leg.id,
                                                    positions = positions
                                                )
                                            )
                                        }
                                    }

                                    is TimedLegDto -> {
                                        val positions = leg.timedLeg?.legTrack?.trackSection?.first()?.linkProjection?.positions
                                        if (!positions.isNullOrEmpty()) {
                                            mapData.add(
                                                MapLibreData(
                                                    id = leg.id,
                                                    positions = positions
                                                )
                                            )
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

    fun updateWalkingSpeed(walkingSpeed: Int) {
        state.update { it.copy(walkingSpeed = walkingSpeed) }
    }

    fun setDirectConnection() {
        state.update { it.copy(isDirectConnection = !state.value.isDirectConnection) }
    }

    fun setFewerTransfers() {
        state.update { it.copy(isFewerTransfers = !state.value.isFewerTransfers) }
    }

    fun toggleVehicle(vehicleType: String) {
        state.update { current ->
            val updated = current.vehicleOptions.map { option ->
                if (option.vehicleType == vehicleType) option.copy(isSelected = !option.isSelected) else option
            }
            current.copy(vehicleOptions = updated)
        }
    }

    fun toggleSubVehicle(vehicleType: String) {
        state.update { current ->
            val updated = current.vehicleSubOptions.map { option ->
                if (option.vehicleType == vehicleType) option.copy(isSelected = !option.isSelected) else option
            }
            current.copy(vehicleSubOptions = updated)
        }
    }

    fun setMinDistance(minDistance: Int?) {
        state.update { it.copy(minDistance = minDistance) }
    }

    fun setMaxDistance(maxDistance: Int?) {
        state.update { it.copy(maxDistance = maxDistance) }
    }

    fun setMinDuration(minDuration: Long?) {
        state.update { it.copy(minDuration = minDuration) }

    }

    fun setMaxDuration(maxDuration: Long?) {
        state.update { it.copy(maxDuration = maxDuration) }
    }

    fun setBikeTransport() {
        state.update { it.copy(isBikeTransport = !state.value.isBikeTransport) }
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
        val mapData: List<MapLibreData> = emptyList(),
        val walkingSpeed: Int = 100,
        val isDirectConnection: Boolean = false,
        val isFewerTransfers: Boolean = false,
        val isBikeTransport: Boolean = false,
        val vehicleOptions: List<VehicleOption> = emptyList(),
        val vehicleSubOptions: List<VehicleOption> = emptyList(),
        val minDuration: Long? = null,
        val maxDuration: Long? = null,
        val minDistance: Int? = null,
        val maxDistance: Int? = null,
    )
}