
package com.braveridge.sensor.client

import com.braveridge.sensor.client.request.*
import com.braveridge.sensor.client.response.*
import retrofit2.Call
import retrofit2.http.*

interface BravegateClient {
    @POST("v1/auth")
    fun auth(@Body authRequest: AuthRequest): Call<AuthResponse>

    @GET("v1/groups")
    fun getGroupList(@Header("X-Braveridge-API-Key") apiKey: String,
                     @Header("X-Braveridge-Token") token: String,
                     @Query("page") page: Int,
                     @Query("limit") limit: Int): Call<GroupListResponse>

    @GET("v1/groups/{id}")
    fun getGroup(@Header("X-Braveridge-API-Key") apiKey: String,
                 @Header("X-Braveridge-Token") token: String,
                 @Path("id") groupId: String): Call<GroupResponse>

    @DELETE("v1/groups/{id}")
    fun deleteGroup(@Header("X-Braveridge-API-Key") apiKey: String,
                    @Header("X-Braveridge-Token") token: String,
                    @Path("id") groupId: String): Call<GroupResponse>

    @GET("v1/routers")
    fun getRouterList(@Header("X-Braveridge-API-Key") apiKey: String,
                      @Header("X-Braveridge-Token") token: String,
                      @Query("page") page: Int,
                      @Query("limit") limit: Int): Call<RouterListResponse>

    @POST("v1/routers")
    fun createRouter(@Header("X-Braveridge-API-Key") apiKey: String,
                     @Header("X-Braveridge-Token") token: String,
                     @Body routerRequest: RouterCreateRequest): Call<RouterResponse>

    @GET("v1/routers/{id}")
    fun getRouter(@Header("X-Braveridge-API-Key") apiKey: String,
                  @Header("X-Braveridge-Token") token: String,
                  @Path("id") routerId: String): Call<RouterResponse>

    @PUT("v1/routers/{id}")
    fun updateRouter(@Header("X-Braveridge-API-Key") apiKey: String,
                     @Header("X-Braveridge-Token") token: String,
                     @Path("id") routerId: String,
                     @Body routerRequest: RouterUpdateRequest): Call<RouterResponse>

    @DELETE("v1/routers/{id}")
    fun deleteRouter(@Header("X-Braveridge-API-Key") apiKey: String,
                     @Header("X-Braveridge-Token") token: String,
                     @Path("id") routerId: String): Call<RouterResponse>

    @GET("v1/routers/{id}/data_usage")
    fun getRouterDataUsage(@Header("X-Braveridge-API-Key") apiKey: String,
                           @Header("X-Braveridge-Token") token: String,
                           @Path("id") routerId: String,
                           @Query("start") start: String,
                           @Query("end") end: String): Call<RouterUsageResponse>

    @GET("v1/devices")
    fun getDeviceList(@Header("X-Braveridge-API-Key") apiKey: String,
                      @Header("X-Braveridge-Token") token: String,
                      @Query("page") page: Int,
                      @Query("limit") limit: Int): Call<DeviceListResponse>

    @POST("v1/devices")
    fun createDevice(@Header("X-Braveridge-API-Key") apiKey: String,
                     @Header("X-Braveridge-Token") token: String,
                     @Body deviceRequest: DeviceCreateRequest): Call<DeviceResponse>

    @GET("v1/devices/{id}")
    fun getDevice(@Header("X-Braveridge-API-Key") apiKey: String,
                  @Header("X-Braveridge-Token") token: String,
                  @Path("id") deviceId: String): Call<DeviceResponse>

    @PUT("v1/devices/{id}")
    fun updateDevice(@Header("X-Braveridge-API-Key") apiKey: String,
                     @Header("X-Braveridge-Token") token: String,
                     @Path("id") deviceId: String,
                     @Body deviceRequest: DeviceUpdateRequest): Call<DeviceResponse>

    @DELETE("v1/devices/{id}")
    fun deleteDevice(@Header("X-Braveridge-API-Key") apiKey: String,
                     @Header("X-Braveridge-Token") token: String,
                     @Path("id") deviceId: String): Call<DeviceResponse>

    @GET("v1/applications")
    fun getApplicationList(@Header("X-Braveridge-API-Key") apiKey: String,
                           @Header("X-Braveridge-Token") token: String,
                           @Query("page") page: Int,
                           @Query("limit") limit: Int): Call<ApplicationListResponse>

    @POST("v1/applications")
    fun createApplication(@Header("X-Braveridge-API-Key") apiKey: String,
                          @Header("X-Braveridge-Token") token: String,
                          @Body applicationRequest: ApplicationRequest): Call<ApplicationResponse>

    @GET("v1/applications/{id}")
    fun getApplication(@Header("X-Braveridge-API-Key") apiKey: String,
                       @Header("X-Braveridge-Token") token: String,
                       @Path("id") applicationId: String): Call<ApplicationResponse>

    @PUT("v1/applications/{id}")
    fun updateApplication(@Header("X-Braveridge-API-Key") apiKey: String,
                          @Header("X-Braveridge-Token") token: String,
                          @Path("id") applicationId: String,
                          @Body applicationRequest: ApplicationRequest): Call<ApplicationResponse>

    @DELETE("v1/applications/{id}")
    fun deleteApplication(@Header("X-Braveridge-API-Key") apiKey: String,
                          @Header("X-Braveridge-Token") token: String,
                          @Path("id") applicationId: String): Call<ApplicationResponse>

    @GET("v1/commands")
    fun getCommands(@Header("X-Braveridge-API-Key") apiKey: String,
                    @Header("X-Braveridge-Token") token: String,
                    @Query("page") page: Int,
                    @Query("limit") limit: Int): Call<CommandsResponse>

    @GET("v1/commands/{id}")
    fun getCommand(@Header("X-Braveridge-API-Key") apiKey: String,
                   @Header("X-Braveridge-Token") token: String,
                   @Path("id") commandId: String): Call<CommandResponse>

    @POST("v1/commands")
    fun postCommand(@Header("X-Braveridge-API-Key") apiKey: String,
                    @Header("X-Braveridge-Token") token: String,
                    @Body commandRequest: CommandRequest): Call<Void>

    @GET("v1/accounts")
    fun getAccount(@Header("X-Braveridge-API-Key") apiKey: String,
                   @Header("X-Braveridge-Token") token: String): Call<AccountListResponse>

    @POST("v1/apikeys")
    fun postApiKey(@Header("X-Braveridge-API-Key") apiKey: String,
                   @Header("X-Braveridge-Token") token: String,
                   @Body emptyRequest: Map<String, String> = mapOf()): Call<ApiKeyResponse>
}