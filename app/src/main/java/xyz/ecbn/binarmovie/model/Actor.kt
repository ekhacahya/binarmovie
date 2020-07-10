package xyz.ecbn.binarmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(
    var cast_id: Int? = null,
    var movieId: Int = 0,
    var personId: Int = 0,
    var character: String? = null,
    var credit_id: String? = null,
    var gender: Int? = null,
    var id: Int,
    var name: String? = null,
    var order: Int? = null,
    var profile_path: String? = null
) : Parcelable