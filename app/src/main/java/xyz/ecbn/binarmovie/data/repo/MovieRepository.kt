package xyz.ecbn.binarmovie.data.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.ecbn.binarmovie.data.ServiceInterface
import xyz.ecbn.binarmovie.model.GenresResponse
import xyz.ecbn.binarmovie.model.Movie
import xyz.ecbn.binarmovie.model.MoviesResponse
import xyz.ecbn.binarmovie.model.ReviewsResponse
import kotlin.coroutines.CoroutineContext

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
class MovieRepository(
    private val serviceInterface: ServiceInterface
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    suspend fun getGenres(): GenresResponse {
        return withContext(Dispatchers.IO) {
            return@withContext serviceInterface.genreMovies()
        }
    }

    suspend fun getMovies(page: Int = 1, genre: String = ""): MoviesResponse {
        return withContext(Dispatchers.IO) {
            return@withContext serviceInterface.getMovies(page, genre)
        }
    }

    suspend fun getMovieReviews(page: Int, id: Int = 1): ReviewsResponse {
        return withContext(Dispatchers.IO) {
            return@withContext serviceInterface.getMovieReviews(id, page)
        }
    }

    suspend fun getMovie(id: Int): Movie {
        return withContext(Dispatchers.IO) {
            return@withContext serviceInterface.getMovie(id)
        }
    }
}