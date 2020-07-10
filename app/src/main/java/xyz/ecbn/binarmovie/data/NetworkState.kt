package xyz.ecbn.binarmovie.data

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
enum class Status {
    RUNNING,
    SUCCESS,
    NO_RESULT,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {
    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        val NO_RESULT: NetworkState = NetworkState(Status.NO_RESULT, "No Result")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Something went wrong")
    }
}