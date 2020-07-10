package xyz.ecbn.binarmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
@Parcelize
data class Movie(
    var id: Int,
    var original_title: String? = null,
    var backdrop_path: String? = null,
    var vote_average: String? = null,
    var vote_count: Int? = 0,
    var release_date: String? = null,
    var runtime: String? = null,
    var overview: String? = null,
    var images: Images? = null,
    var videos: VideoCollection? = null,
    var credits: Credits? = null,
    var genres: List<Genre>? = null,
    var poster_path: String? = null
) : Parcelable