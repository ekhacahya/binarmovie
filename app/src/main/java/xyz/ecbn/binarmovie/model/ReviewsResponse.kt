package xyz.ecbn.binarmovie.model

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
data class ReviewsResponse(
    var id: Int,
    var page: Int = 0,
    var total_pages: Int = 0,
    var total_results: Int = 0,
    var results: ArrayList<Review> = arrayListOf()
)