package xyz.ecbn.binarmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * BinarMovie Created by ecbn on 10/07/20.
 */
@Parcelize
data class Images(
    var backdrops: ArrayList<Image> = arrayListOf(),
    var profiles: ArrayList<Image> = arrayListOf(),
    var posters: ArrayList<Image> = arrayListOf()
) : Parcelable