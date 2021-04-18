package com.braveridge.sensor.config

import com.braveridge.sensor.client.BravegateClient
import com.braveridge.sensor.properties.BravegateProperties
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
class AppConfig
{
    // Json -> Java Object Converter のインスタンス生成
    @Bean
    fun jacksonConverterFactory(mapper: ObjectMapper): JacksonConverterFactory = JacksonConverterFactory.create(mapper)

    // HTTPクライアントのインスタンス生成
    @Bean
    fun bravegateRetrofit(jacksonConverterFactory: JacksonConverterFactory, bravegateProperties: BravegateProperties): Retrofit
            = Retrofit.Builder()
                .baseUrl(bravegateProperties.apiUrl)
                .addConverterFactory(jacksonConverterFactory)
                .client(OkHttpClient.Builder().build())
                .build()

    // Bravegateクライアントのインタンス生成
    @Bean
    fun bravegateClient(bravegateRetrofit: Retrofit) = bravegateRetrofit.create(BravegateClient::class.java)
}