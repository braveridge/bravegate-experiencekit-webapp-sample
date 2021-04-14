package com.braveridge.sensor.client

import org.springframework.stereotype.Component
import retrofit2.Call

@Component
class RetrofitExecutor{
    fun <T> execute(call: Call<T>): RetrofitResult<T> {
        val response = call.execute()
        if (response.isSuccessful) {
            return RetrofitResult(false, response.headers().toMultimap(), response.body(), null)
        }
        val errorBody = response.errorBody()?.string()
        return RetrofitResult(true, response.headers().toMultimap(), null, errorBody)
    }
}