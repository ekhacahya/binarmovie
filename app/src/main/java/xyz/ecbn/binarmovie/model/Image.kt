package xyz.ecbn.binarmovie.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * BinarMovie Created by ecbn on 10/07/20.
 */
@Parcelize
data class Image(
    var id: Int,
    var movieId: Int = 0,
    var personId: Int = 0,
    var type: String? = ImageType.POSTER.name,
    var aspect_ratio: Double? = null,
    var file_path: String? = null,
    var height: Int? = null,
    var iso_639_1: String? = null,
    var vote_average: Double? = null,
    var vote_count: Int? = null,
    var width: Int? = null
) : Parcelable

enum class ImageType {
    POSTER,
    BACKDROP
}