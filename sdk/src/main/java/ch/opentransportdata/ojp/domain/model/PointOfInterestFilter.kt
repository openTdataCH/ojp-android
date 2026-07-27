package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 02.07.2026
 *
 */
@Parcelize
data class PointOfInterestFilter(
    val categories: List<PointOfInterestCategory> = emptyList(),
    val exclude: Boolean? = null,
) : Parcelable

@Parcelize
data class PointOfInterestCategory(
    val osmTag: OsmTag? = null,
    val classification: String? = null,
) : Parcelable {

    companion object {
        fun sharing(category: SharingCategory): PointOfInterestCategory =
            PointOfInterestCategory(osmTag = OsmTag(tag = "amenity", value = category.value))
    }
}

@Parcelize
data class OsmTag(
    val tag: String,
    val value: String,
) : Parcelable
