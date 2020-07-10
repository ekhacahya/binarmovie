package xyz.ecbn.binarmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoCollection(
    var id: Int? = null,
    var results: ArrayList<Video> = arrayListOf()
) : Parcelable