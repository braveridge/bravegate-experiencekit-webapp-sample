package com.braveridge.sensor.client

import com.braveridge.sensor.client.RetrofitExecutor
import com.braveridge.sensor.client.RetrofitResult
import com.braveridge.sensor.client.request.AuthRequest
import com.braveridge.sensor.client.response.AuthResponse
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class AuthTokenResolver(private val client: BravegateClient,
                        private val executor: RetrofitExecutor) {

    companion object CacheKey {
        const val ResolveAuthToken = "AuthTokenResolver.resolveAuthToken"
    }

    @Cacheable(AuthTokenResolver.ResolveAuthToken)
    fun resolve(authkeyId: String, authkeySecret: String): AuthToken? {
        val authResult = auth(authkeyId, authkeySecret)
        return if (authResult.isSuccessed) {
            AuthToken(authResult.successBody!!.apiKey, authResult.successBody.token)
        } else {
            null
        }
    }

    private fun auth(authkeyId: String, authkeySecret: String, timeout: Duration = Duration.ofDays(1)): RetrofitResult<AuthResponse> {
        val request = AuthRequest(authkeyId, authkeySecret, timeout.toSeconds())
        val call = client.auth(request)
        return executor.execute(call)
    }


}