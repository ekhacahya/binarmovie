package xyz.ecbn.binarmovie.model

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
data class MoviesResponse(
    var page: Int = 0,
    var total_pages: Int = 0,
    var total_results: Int = 0,
    var results: ArrayList<Movie> = arrayListOf()
)