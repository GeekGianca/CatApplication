package com.gksoftwaresolutions.catapp.component

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    END_OF_LIST,
    EMPTY
}

class NetworkState(val status: Status, val msg: String) {
    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Failed to load votes")
        val END_OF_LIST: NetworkState = NetworkState(Status.END_OF_LIST, "No more votes")
        val EMPTY: NetworkState = NetworkState(Status.EMPTY, "Empty list votes")
    }
}