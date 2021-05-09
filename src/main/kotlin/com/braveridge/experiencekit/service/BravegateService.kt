package com.braveridge.experiencekit.service

import com.braveridge.experiencekit.client.*
import com.braveridge.experiencekit.client.response.*
import com.braveridge.experiencekit.client.request.*
import com.braveridge.experiencekit.client.exception.BravegateException
import com.braveridge.experiencekit.properties.BravegateProperties
import com.braveridge.experiencekit.utility.toLocal
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.io.Serializable
import java.lang.RuntimeException
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest


@Service
class BravegateService(
    private val client: BravegateClient,
    private val properties: BravegateProperties,
    private val executor: RetrofitExecutor,
    private val authTokenResolver: AuthTokenResolver
) {
    companion object CacheKey {
        const val RouterList = "BravegateService.routerList"
        const val DeviceList = "BravegateService.deviceList"
    }

    // ルーター一覧取得
    fun getRouterList(page: Int = 1, limit: Int = 100): List<Router> {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return listOf()
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
    fun getRouter(routerId: String): Router? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
        val call = client.getRouter(authToken.apiKey, authToken.token, routerId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return null
        }
        return bvgResult.successBody?.let { Router(it) }
    }

    // ルーター登録
    @CacheEvict(CacheKey.RouterList, key = "#authkeyId")
    fun createRouter(request: RouterCreateRequest): Router? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
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
    fun deleteRouter(routerId: String): Boolean {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return false
        val call = client.deleteRouter(authToken.apiKey, authToken.token, routerId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return false
        }

        return bvgResult.isSuccess
    }

    // デバイス一覧取得
    fun getDeviceList(page: Int = 1, limit: Int = 100): List<Device> {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return listOf()
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
    fun getDevice(deviceId: String): Device? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
        val call = client.getDevice(authToken.apiKey, authToken.token, deviceId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return null
        }
        return bvgResult.successBody?.let { Device(it) }
    }

    // デバイス登録
    @CacheEvict(CacheKey.DeviceList, key = "#authkeyId")
    fun createDevice(request: DeviceCreateRequest): Device? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
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
    fun updateDevice(
        deviceId: String,
        request: DeviceUpdateRequest
    ): Device? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
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
    fun deleteDevice(deviceId: String): Boolean {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return false
        val call = client.deleteDevice(authToken.apiKey, authToken.token, deviceId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return false
        }
        return bvgResult.isSuccess
    }

    //アプリケーション一覧取得
    fun getApplicationList(
        page: Int = 1,
        limit: Int = 100
    ): List<Application> {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return listOf()
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
    fun getApplication(applicationId: String): Application? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
        val call = client.getApplication(authToken.apiKey, authToken.token, applicationId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            return null
        }
        return bvgResult.successBody?.let { Application(it) }
    }

    //アプリケーション登録
    fun createApplication(request: ApplicationRequest): Application? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
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
    fun updateApplication(
        applicationId: String,
        request: ApplicationRequest
    ): Application? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
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
    fun deleteApplication(applicationId: String): Boolean {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return false
        val call = client.deleteApplication(authToken.apiKey, authToken.token, applicationId)
        val bvgResult = executor.execute(call)
        if (bvgResult.hasError) {
            bvgResult.errorBody?.let {
                throw BravegateException(it)
            } ?: return false
        }
        return bvgResult.isSuccess
    }

    // アプリケーショントークンチェック
    fun checkAppToken(jsonString: String,
                      request: HttpServletRequest
    ): Boolean {
        val map = jacksonObjectMapper().readValue<Map<Any, Any>>(jsonString)
        val application = map["application"] as? Map<*, *>
        val applicationId = application?.get("application_id") as? String ?: return false
        val token = getApplication(applicationId)?.settings?.token
        val tokenHeader = request.getHeader("X-Braveridge-Webhook-Token")
        if (true == token?.isNotEmpty()) {
            if (token != tokenHeader) return false
        }
        return true
    }

    // コマンド実行
    fun sendCommand(request: CommandRequest): String? {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return null
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
    fun getCommandList(page: Int = 1, limit: Int = 100): List<Command> {
        val authToken = authTokenResolver.resolve(properties.authkeyId, properties.authkeySecret) ?: return listOf()
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
    ) : Serializable {
        // センサー
        data class Sensor(
            val sensorId: String,
            val name: String,
            val executableCommands: List<String>
        ) : Serializable {
            constructor(src: DeviceResponse.Sensor) : this(src.sensorId, src.name, src.executableCommands.toList())
        }

        constructor(src: DeviceResponse) : this(
            src.groupId,
            src.deviceId,
            src.name,
            src.sensors,
            src.createdAt.toLocal(),
            src.updatedAt.toLocal()
        )
    }

    // ルーター
    data class Router(
        val groupId: String,
        val routerId: String,
        val imsi: String,
        val rssi: Int,
        val battery: Int,
        val simStatus: String,
        val name: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    ) : Serializable {
        constructor(src: RouterResponse) : this(
            src.groupId, src.routerId, src.imsi, src.rssi, src.battery,
            src.simStatus, src.name, src.createdAt.toLocal(), src.updatedAt.toLocal()
        )
    }

    // アプリケーション
    data class Application(
        val groupId: String,
        val applicationId: String,
        val name: String,
        val applicationType: String,
        val settings: Settings,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    ) : Serializable {

        data class Settings(val url: String, val token: String?) : Serializable

        constructor(src: ApplicationResponse) : this(
            src.groupId, src.applicationId, src.name, src.applicationType,
            Settings(src.settings.url, src.settings.token ?: ""), src.createdAt.toLocal(), src.updatedAt.toLocal()
        )
    }

    // コマンド
    data class Command(
        val commandId: String,
        val name: String,
        val url: String,
        val processedStatusCount: String,
        val rejectedStatusCount: String,
        val pendingStatusCount: String,
        val status: String?,
        val acceptedAt: LocalDateTime
    ) {
        constructor(src: CommandsResponse.Command) : this(
            src.commandId, src.name, src.url,
            src.statusCounts.processed.toString(),
            src.statusCounts.rejected.toString(),
            src.statusCounts.pending.toString(),
            src.status, src.acceptedAt.toLocal()
        )
    }

}