package xyz.ecbn.binarmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * BinarMovie Created by ecbn on 10/07/20.
 */
@Parcelize
data class Review(
    var author: String? = null,
    var content: String? = null,
    var url: String? = null,
    var id: String? = null
) : Parcelable