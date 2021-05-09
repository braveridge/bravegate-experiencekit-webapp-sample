package com.braveridge.experiencekit.service


import com.braveridge.experiencekit.db.entity.SensorLogEntity
import com.braveridge.experiencekit.form.WebhookForm
import com.braveridge.experiencekit.properties.BravegateProperties
import com.braveridge.experiencekit.repository.SensorLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SensorLogService(
    private val repository: SensorLogRepository,
    private val properties: BravegateProperties
) {

    fun findAll(
        pager: Pager,
        appId: String? = null,
        routerId: String? = null,
        deviceId: String? = null,
        sensorId: String? = null
    ) = repository.findAll(
        pager.countPerPage, (pager.currentPage - 1) * pager.countPerPage,
        if (appId?.isNotEmpty() == true) appId else null,
        if (routerId?.isNotEmpty() == true) routerId else null,
        if (deviceId?.isNotEmpty() == true) deviceId else null,
        if (sensorId?.isNotEmpty() == true) sensorId else null
    )

    fun totalCount(
        appId: String? = null,
        routerId: String? = null,
        deviceId: String? = null,
        sensorId: String? = null
    ) = repository.totalCount(
        if (appId?.isNotEmpty() == true) appId else null,
        if (routerId?.isNotEmpty() == true) routerId else null,
        if (deviceId?.isNotEmpty() == true) deviceId else null,
        if (sensorId?.isNotEmpty() == true) sensorId else null
    )

    fun create(form: WebhookForm, jsonString: String): SensorLogEntity {
        val entity = convertToEntity(form, jsonString)
        return repository.create(entity)
    }

    fun convertToEntity(form: WebhookForm, jsonString: String) = SensorLogEntity(
        0, form.application.applicationId, form.application.name,
        form.router.routerId, form.router.imsi, form.router.rssi, form.router.battery, form.router.fwVersion,
        form.device.deviceId, form.device.sensorId, form.device.sensorName, form.device.rssi,
        form.device.data.toString(),
        form.uplinkId, form.date.toLocalDateTime(),
        jsonString
    )
}