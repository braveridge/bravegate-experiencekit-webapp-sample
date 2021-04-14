package com.braveridge.sensor.service

import com.braveridge.sensor.client.*
import com.braveridge.sensor.client.response.*
import com.braveridge.sensor.client.request.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BravegateService(private val client:BravegateClient,
                       private val executor: RetrofitExecutor,
                       private val authTokenResolver: AuthTokenResolver)
{
    // ルーター一覧取得
    //fun getRouterList()

    // ルーター削除
    //fun delRouter()

    // ルーター登録
    //fun addRouter()

    // デバイス一覧取得
    //fun getDeviceList()

    // デバイス登録
    //fun addDevice()

    // デバイス削除
    //fun delDevice()

    //アプリケーション一覧取得
    //fun getApplication()

    //アプリケーション登録
    //fun addApplication()

    // アプリケーション削除
    //fun delApplication()
}