package com.braveridge.sensor.controller

import com.braveridge.sensor.properties.BravegateProperties
import com.braveridge.sensor.service.BravegateService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.auth0.jwt.exceptions.JWTCreationException
import com.braveridge.sensor.client.request.ApplicationRequest
import com.braveridge.sensor.form.ApplicationForm
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

@Controller
@RequestMapping("")
class AppController(){
    fun index(): String{
        return "index"
    }
}

@Controller
@RequestMapping("/application")
class ApplicationController(private val bvgService: BravegateService,
                            private val properties: BravegateProperties)
{
    // 取得（1件）
    @GetMapping("")
    fun list(model: Model):String {
        var application: BravegateService.Application? = null
        val applicationList = bvgService.getApplicationList(properties.authkeyId, properties.authkeySecret)
        if (applicationList.isNotEmpty()) {
            application = applicationList[0]
        }
        application?.also { model.addAttribute("app", it) }
                   ?:run {
                       val app = BravegateService.Application("","","","",BravegateService.Application.Settings("",""),
                           LocalDateTime.MAX,LocalDateTime.MAX)
                       model.addAttribute("app", app)
                   }
        return "application/edit"
    }

    // 登録
    @PostMapping("create")
    @ResponseBody
    fun create(model: Model,
               @RequestBody @Validated form: ApplicationForm):BravegateService.Application? {
        var token:String? = null
        if(form.token?.length!! >0){
            token = form.token
        }
        val request = ApplicationRequest("", ApplicationRequest.Settings(form.url, token), "webhook", form.name)

        val applicationList = bvgService.getApplicationList(properties.authkeyId, properties.authkeySecret)
        for (app in applicationList){
            bvgService.deleteApplication(properties.authkeyId, properties.authkeySecret, app.applicationId)
        }
        return bvgService.createApplication(properties.authkeyId, properties.authkeySecret, request)
            ?: throw RuntimeException()
    }

    // 更新
    @PostMapping("update")
    @ResponseBody
    fun update(model: Model,
               @RequestBody @Validated form: ApplicationForm):BravegateService.Application {
        var token:String? = null
        if(form.token?.length!! >0){
            token = form.token
        }
        val request = ApplicationRequest("", ApplicationRequest.Settings(form.url, token), "webhook", form.name)
        return bvgService.updateApplication(properties.authkeyId, properties.authkeySecret, form.applicationId, request)
            ?: throw RuntimeException()
    }
}

@RestController
@RequestMapping("application/token")
class ApplicationTokenController(private val properties: BravegateProperties){
    // トークン取得
    @GetMapping("")
    fun creatToken():String{
        lateinit var token : String
        try {
            val algorithm: com.auth0.jwt.algorithms.Algorithm = HMAC256(properties.authkeySecret)
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.add(Calendar.SECOND, 86400)
            val expireTime = calendar.time
            token = JWT.create()
                .withIssuer("user")
                .withClaim("name", "brave-taro")
                .withExpiresAt(expireTime)
                .sign(algorithm)
        } catch (e : JWTCreationException) {
            //Invalid Signing
        }
        return token
    }
}