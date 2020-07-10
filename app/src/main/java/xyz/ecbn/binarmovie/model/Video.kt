package xyz.ecbn.binarmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * BinarMovie Created by ecbn on 10/07/20.
 */
@Parcelize
data class Video(
    var id: String,
    var movieId: Int? = 0,
    var iso_3166_1: String? = null,
    var iso_639_1: String? = null,
    var key: String? = null,
    var name: String? = null,
    var site: String? = null,
    var size: Int? = null,
    var type: String? = null
) : Parcelable