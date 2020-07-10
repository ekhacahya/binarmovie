package xyz.ecbn.binarmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credits(
    var cast: ArrayList<Actor> = arrayListOf()
) : Parcelable