package xyz.ecbn.binarmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
@Parcelize
data class Genre(
    var id: Int = 0,
    var name: String? = ""
) : Parcelable