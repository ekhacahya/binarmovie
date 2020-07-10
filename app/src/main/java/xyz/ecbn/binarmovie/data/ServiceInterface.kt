package xyz.ecbn.binarmovie.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.ecbn.binarmovie.model.GenresResponse
import xyz.ecbn.binarmovie.model.Movie
import xyz.ecbn.binarmovie.model.MoviesResponse
import xyz.ecbn.binarmovie.model.ReviewsResponse

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
interface ServiceInterface {
    @GET(value = "3/genre/movie/list")
    suspend fun genreMovies(
        @Query("language") language: String = "en-US"
    ): GenresResponse

    @GET(value = "3/discover/movie")
    suspend fun getMovies(
        @Query(value = "page") pageNumber: Int = 1,
        @Query(value = "with_genres") withGenre: String
    ): MoviesResponse

    @GET("3/movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): Movie

    @GET("3/movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") id: Int,
        @Query(value = "page") pageNumber: Int = 1
    ): ReviewsResponse
}