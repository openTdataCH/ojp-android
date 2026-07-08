package ch.opentransportdata.presentation.feature.map

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.PointOfInterestCategory
import ch.opentransportdata.ojp.domain.model.PointOfInterestFilter
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.SharingCategory
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.model.sharingCategories
import ch.opentransportdata.presentation.MainActivity
import ch.opentransportdata.presentation.util.toOjpLanguageCode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

/**
 * Created by Deniz Kalem on 08.07.2026
 *
 */
class SharedMobilityMapViewModel : ViewModel() {

    val state = MutableStateFlow(UiState())

    private var loadJob: Job? = null

    fun toggleCategory(category: SharingCategory) {
        val selected = state.value.selectedCategories.toMutableSet()
        if (!selected.add(category)) selected.remove(category)
        state.update { it.copy(selectedCategories = selected) }
    }

    fun selectPoi(id: String?) {
        state.update { current -> current.copy(selectedPoi = current.pois.firstOrNull { it.id == id }) }
    }

    fun clearSelectedPoi() {
        state.update { it.copy(selectedPoi = null) }
    }

    fun loadPois(
        upperLeftLongitude: Double,
        upperLeftLatitude: Double,
        lowerRightLongitude: Double,
        lowerRightLatitude: Double
    ) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            val result = MainActivity.ojpSdk.requestLocationsFromRectangle(
                languageCode = Locale.getDefault().language.toOjpLanguageCode(),
                upperLeftLongitude = upperLeftLongitude,
                upperLeftLatitude = upperLeftLatitude,
                lowerRightLongitude = lowerRightLongitude,
                lowerRightLatitude = lowerRightLatitude,
                restrictions = LocationInformationParams(
                    types = listOf(PlaceTypeRestriction.POI),
                    numberOfResults = 100,
                    ptModeIncluded = false,
                    pointOfInterestFilter = PointOfInterestFilter(
                        categories = SharingCategory.entries.map { PointOfInterestCategory.sharing(it) }
                    )
                )
            )

            when (result) {
                is Result.Success -> {
                    val markers = result.data.mapIndexedNotNull { index, placeResult ->
                        val place = placeResult.place ?: return@mapIndexedNotNull null
                        val position = place.position ?: return@mapIndexedNotNull null
                        val poi = place.pointOfInterest
                        val name = poi?.name?.text ?: place.name?.text.orEmpty()
                        PoiMarker(
                            id = poi?.publicCode ?: "index_$index",
                            latitude = position.latitude,
                            longitude = position.longitude,
                            name = name,
                            category = poi?.sharingCategories?.firstOrNull() ?: classifyByName(name),
                            additionalInformation = poi?.additionalInformation.orEmpty()
                        )
                    }
                    Log.d(TAG, "Loaded ${result.data.size} place results -> ${markers.size} markers")
                    state.update { it.copy(pois = markers, isLoading = false) }
                }

                is Result.Error -> {
                    if (result.error !is OjpError.RequestCancelled) {
                        Log.e(TAG, "Error loading POIs", result.error.exception)
                        postEvent(Event.ShowSnackBar("Could not load points of interest"))
                    }
                    state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun classifyByName(name: String): SharingCategory? {
        val value = name.lowercase(Locale.getDefault())
        return when {
            listOf("scooter", "trottinett").any { it in value } -> SharingCategory.E_SCOOTER
            listOf("charg", "ladestation", "lade").any { it in value } -> SharingCategory.CHARGING_STATION
            listOf("velo", "bike", "fahrrad", "zweirad", "cycle").any { it in value } -> SharingCategory.BIKE
            listOf("car", "auto", "mobility", "sharing").any { it in value } -> SharingCategory.CAR
            else -> null
        }
    }

    fun eventHandled(id: Long) {
        state.update { current -> current.copy(events = current.events.filterNot { it.id == id }) }
    }

    private fun postEvent(event: Event) {
        state.update { it.copy(events = it.events + event) }
    }

    sealed class Event(val id: Long = UUID.randomUUID().mostSignificantBits) {
        data class ShowSnackBar(val message: String) : Event()
    }

    @Immutable
    data class UiState(
        val pois: List<PoiMarker> = emptyList(),
        val selectedCategories: Set<SharingCategory> = SharingCategory.entries.toSet(),
        val selectedPoi: PoiMarker? = null,
        val isLoading: Boolean = false,
        val events: List<Event> = emptyList()
    ) {
        val visiblePois: List<PoiMarker>
            get() = pois.filter { it.category != null && it.category in selectedCategories }
    }

    companion object {
        private const val TAG = "SharedMobilityMapVM"
    }
}

@Immutable
data class PoiMarker(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val category: SharingCategory?,
    val additionalInformation: Map<String, String> = emptyMap()
)