package com.braveridge.experiencekit.client

data class RetrofitResult<T>(
    val hasError: Boolean,
    val headers: Map<String, List<String>>,
    val successBody: T?,
    val errorBody: String?
) {
    val isSuccess: Boolean
        get() = !hasError
}