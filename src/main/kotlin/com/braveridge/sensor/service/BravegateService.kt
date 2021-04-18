package com.braveridge.sensor.service

import com.braveridge.sensor.client.*
import com.braveridge.sensor.client.response.*
import com.braveridge.sensor.client.request.*
import com.braveridge.sensor.client.exception.BravegateException
import com.braveridge.sensor.utility.toLocal
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import java.io.Serializable
import java.time.LocalDateTime


@Service
class BravegateService(private val client: BravegateClient,
                       private val executor: RetrofitExecutor,
                       private val authTokenResolver: AuthTokenResolver)
{
    companion object CacheKey {
        const val RouterList = "BravegateService.routerList"
        const val DeviceList = "BravegateService.deviceList"
    }

    // ルーター一覧取得
    fun getRouterList(authkeyId: String, authkeySecret: String, page: Int = 1, limit: Int = 100): List<Router> {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return listOf()

        val call = client.getRouterList(authToken.apiKey, authToken.token, page, limit)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return listOf()
        }
        return bvgResult.successBody
            ?.routers
            ?.sortedBy { it.routerId }
            ?.map { Router(it) }
            ?: listOf()
    }

    // ルーター取得
    fun getRouter(authkeyId: String, authkeySecret: String, routerId: String): Router? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.getRouter(authToken.apiKey, authToken.token, routerId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return null
        }
        return bvgResult.successBody?.let { Router(it) }
    }

    // ルーター登録
    @CacheEvict(CacheKey.RouterList, key = "#authkeyId")
    fun createRouter(authkeyId: String, authkeySecret: String, request: RouterCreateRequest): Router? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.createRouter(authToken.apiKey, authToken.token, request)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return null
        }
        return bvgResult.successBody?.let { Router(it) }
    }

    // ルーター削除
    @CacheEvict(CacheKey.RouterList, key = "#authkeyId")
    fun deleteRouter(authkeyId: String, authkeySecret: String, routerId: String): Boolean {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return false
        val call = client.deleteRouter(authToken.apiKey, authToken.token, routerId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return false
        }

        return bvgResult.isSuccessed
    }

    // デバイス一覧取得
    fun getDeviceList(authkeyId: String, authkeySecret: String, page: Int = 1, limit: Int = 100): List<Device> {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return listOf()
        val call = client.getDeviceList(authToken.apiKey, authToken.token, page, limit)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return listOf()
        }
        return bvgResult.successBody
            ?.devices
            ?.sortedBy { it.deviceId }
            ?.map { Device(it) }
            ?: listOf()
    }

    // デバイス取得
    fun getDevice(authkeyId: String, authkeySecret: String, deviceId: String): Device? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.getDevice(authToken.apiKey, authToken.token, deviceId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return null
        }
        return bvgResult.successBody?.let { Device(it) }
    }

    // デバイス登録
    @CacheEvict(CacheKey.DeviceList, key = "#authkeyId")
    fun createDevice(authkeyId: String, authkeySecret: String, request: DeviceCreateRequest): Device? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.createDevice(authToken.apiKey, authToken.token, request)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return null
        }
        return bvgResult.successBody?.let { Device(it) }
    }

    // デバイス更新
    @CacheEvict(CacheKey.DeviceList, key = "#authkeyId")
    fun updateDevice(authkeyId: String, authkeySecret: String, deviceId: String, request: DeviceUpdateRequest): Device? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.updateDevice(authToken.apiKey, authToken.token, deviceId, request)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return null
        }
        return bvgResult.successBody?.let { Device(it) }
    }

    // デバイス削除
    @CacheEvict(CacheKey.DeviceList, key = "#authkeyId")
    fun deleteDevice(authkeyId: String, authkeySecret: String, deviceId: String): Boolean {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return false
        val call = client.deleteDevice(authToken.apiKey, authToken.token, deviceId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return false
        }
        return bvgResult.isSuccessed
    }

    //アプリケーション一覧取得
    fun getApplicationList(authkeyId: String, authkeySecret: String, page: Int = 1, limit: Int = 100): List<Application> {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return listOf()
        val call = client.getApplicationList(authToken.apiKey, authToken.token, page, limit)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return listOf()
        }
        return bvgResult.successBody
            ?.applications
            ?.sortedBy { it.createdAt }
            ?.map { Application(it) }
            ?: listOf()
    }

    // アプリケーション取得
    fun getApplication(authkeyId: String, authkeySecret: String, applicationId: String): Application? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.getApplication(authToken.apiKey, authToken.token, applicationId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return null
        }
        return bvgResult.successBody?.let { Application(it) }
    }

    //アプリケーション登録
    fun createApplication(authkeyId: String, authkeySecret: String, request: ApplicationRequest): Application? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.createApplication(authToken.apiKey, authToken.token, request)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return null
        }
        return bvgResult.successBody?.let { Application(it) }
    }

    // アプリケーション更新
    fun updateApplication(authkeyId: String, authkeySecret: String, applicationId: String, request: ApplicationRequest): Application? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.updateApplication(authToken.apiKey, authToken.token, applicationId, request)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return null
        }
        return bvgResult.successBody?.let { Application(it) }
    }

    // アプリケーション削除
    fun deleteApplication(authkeyId: String, authkeySecret: String, applicationId: String): Boolean {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return false
        val call = client.deleteApplication(authToken.apiKey, authToken.token, applicationId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return false
        }
        return bvgResult.isSuccessed
    }

    // コマンド実行
    fun sendCommand(authkeyId: String, authkeySecret: String, request: CommandRequest): String? {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return null
        val call = client.postCommand(authToken.apiKey, authToken.token, request)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return null
        }
        // commandIdを返却
        return bvgResult.headers["location"]?.firstOrNull()?.split("/")?.lastOrNull()
    }

    // 実行コマンド一覧取得
    fun getCommandList(authkeyId: String, authkeySecret: String, page: Int = 1, limit: Int = 100): List<Command> {
        val authToken = authTokenResolver.resolve(authkeyId, authkeySecret) ?: return listOf()
        val call = client.getCommands(authToken.apiKey, authToken.token, page, limit)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return listOf()
        }
        return bvgResult.successBody
            ?.commands
            ?.sortedByDescending { it.acceptedAt }
            ?.map { Command(it) }
            ?: listOf()
    }

    //
    // 受け渡し用クラス定義
    //
    // デバイス
    data class Device(
        val groupId: String,
        val deviceId: String,
        val name: String,
        val sensors: List<DeviceResponse.Sensor>,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    ): Serializable {
        // センサー
        data class Sensor(val sensorId: String,
                          val name: String,
                          val executableCommands: List<String>): Serializable {
            constructor( src: DeviceResponse.Sensor): this( src.sensorId, src.name, src.executableCommands.toList() )
        }
        constructor(src: DeviceResponse):this( src.groupId, src.deviceId, src.name, src.sensors, src.createdAt.toLocal(), src.updatedAt.toLocal() )
    }

    // ルーター
    data class Router(val groupId: String,
                      val routerId: String,
                      val imsi: String,
                      val rssi: Int,
                      val battery: Int,
                      val simStatus: String,
                      val name: String,
                      val createdAt: LocalDateTime,
                      val updatedAt: LocalDateTime): Serializable {
        constructor(src: RouterResponse): this( src.groupId, src.routerId, src.imsi, src.rssi, src.battery,
            src.simStatus, src.name, src.createdAt.toLocal(), src.updatedAt.toLocal())
    }

    // アプリケーション
    data class Application(val groupId: String,
                           val applicationId: String,
                           val name: String,
                           val applicationType: String,
                           val settings: Settings,
                           val createdAt: LocalDateTime,
                           val updatedAt: LocalDateTime) : Serializable {

        data class Settings(val url: String, val token: String?) : Serializable

        constructor(src: ApplicationResponse): this(src.groupId, src.applicationId, src.name, src.applicationType,
            Settings(src.settings.url, src.settings.token?:""), src.createdAt.toLocal(), src.updatedAt.toLocal())
    }

    // コマンド
    data class Command(val commandId: String,
                       val name: String,
                       val url: String,
                       val processedStatusCount: String,
                       val rejectedStatusCount: String,
                       val pendingStatusCount: String,
                       val status: String?,
                       val acceptedAt: LocalDateTime) {
        constructor(src: CommandsResponse.Command): this(src.commandId, src.name, src.url,
                                                         src.statusCounts.processed.toString(),
                                                         src.statusCounts.rejected.toString(),
                                                         src.statusCounts.pending.toString(),
                                                         src.status, src.acceptedAt.toLocal())
    }
}