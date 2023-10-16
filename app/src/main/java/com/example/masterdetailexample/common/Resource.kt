package com.example.masterdetailexample.common

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        SUCCESS,
        KNOWN_ERROR,
        LOADING,
        UNKNOWN_ERROR,
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> knownError(message: String, data: T? = null): Resource<T> {
            return Resource(Status.KNOWN_ERROR, data, message)
        }

        fun <T> unknownError(message: String, data: T? = null): Resource<T> {
            return Resource(Status.UNKNOWN_ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
