package xyz.ecbn.binarmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.ecbn.binarmovie.data.NetworkState
import xyz.ecbn.binarmovie.data.Status
import xyz.ecbn.binarmovie.data.repo.MovieRepository
import xyz.ecbn.binarmovie.model.Genre
import xyz.ecbn.binarmovie.model.Movie
import xyz.ecbn.binarmovie.model.Review

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val TAG = MovieViewModel::class.java.simpleName
    private val _networkState = MutableLiveData<NetworkState>()
    private val _genresState = MutableLiveData<List<Genre>>()
    private val _reviewsState = MutableLiveData<List<Review>>()
    private val _moviesState = MutableLiveData<List<Movie>>()
    private val _movieState = MutableLiveData<Movie>()

    val network: LiveData<NetworkState>
        get() = _networkState
    val genres: LiveData<List<Genre>>
        get() = _genresState
    val movies: LiveData<List<Movie>>
        get() = _moviesState
    val reviews: LiveData<List<Review>>
        get() = _reviewsState
    val movie: LiveData<Movie>
        get() = _movieState

    fun getMovie(id: Int) {
        viewModelScope.launch {
            runCatching {
                _networkState.postValue(NetworkState.LOADING)
                movieRepository.getMovie(id)
            }.onSuccess {
                _networkState.postValue(NetworkState.LOADED)
                _movieState.postValue(it)
            }.onFailure {
                _networkState.postValue(NetworkState(Status.FAILED, it.message.toString()))
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch {
            runCatching {
                _networkState.postValue(NetworkState.LOADING)
                movieRepository.getGenres()
            }.onSuccess {
                _networkState.postValue(NetworkState.LOADED)
                _genresState.postValue(it.genres)
            }.onFailure {
                _networkState.postValue(NetworkState(Status.FAILED, it.message.toString()))
            }
        }
    }

    fun getMovies(page: Int = 1, genre: String = "") {
        viewModelScope.launch {
            runCatching {
                _networkState.postValue(NetworkState.LOADING)
                movieRepository.getMovies(page, genre)
            }.onSuccess {
                if (it.total_results == 0) {
                    _networkState.postValue(NetworkState.NO_RESULT)
                } else {
                    _networkState.postValue(NetworkState.LOADED)
                    _moviesState.postValue(it.results)
                }
            }.onFailure {
                _networkState.postValue(NetworkState(Status.FAILED, it.message.toString()))
            }
        }
    }

    fun getMovieReviews(page: Int = 1, id: Int) {
        viewModelScope.launch {
            runCatching {
                _networkState.postValue(NetworkState.LOADING)
                movieRepository.getMovieReviews(page, id)
            }.onSuccess {
                _networkState.postValue(NetworkState.LOADED)
                if (it.total_results == 0) {
                    _networkState.postValue(NetworkState.NO_RESULT)
                } else {
                    _networkState.postValue(NetworkState.LOADED)
                    _reviewsState.postValue(it.results)
                }
            }.onFailure {
                _networkState.postValue(NetworkState(Status.FAILED, it.message.toString()))
            }
        }
    }

}