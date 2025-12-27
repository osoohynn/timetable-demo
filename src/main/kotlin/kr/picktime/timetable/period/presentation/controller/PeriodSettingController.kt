package kr.picktime.timetable.period.presentation.controller

import kotlinx.coroutines.flow.toList
import kr.picktime.timetable.period.application.service.PeriodSettingService
import kr.picktime.timetable.period.domain.entity.PeriodSettingEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek

@RestController
@RequestMapping("/api/period-settings")
class PeriodSettingController(
    private val periodSettingService: PeriodSettingService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createPeriodSetting(@RequestBody request: CreatePeriodSettingRequest): PeriodSettingEntity =
        periodSettingService.createPeriodSetting(
            schoolId = request.schoolId,
            dayOfWeek = request.dayOfWeek,
            maxPeriod = request.maxPeriod
        )

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getPeriodSettingById(@PathVariable id: Long): PeriodSettingEntity =
        periodSettingService.getPeriodSettingById(id)

    @GetMapping("/school/{schoolId}/day/{dayOfWeek}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getPeriodSettingBySchoolIdAndDayOfWeek(
        @PathVariable schoolId: Long,
        @PathVariable dayOfWeek: DayOfWeek
    ): PeriodSettingEntity =
        periodSettingService.getPeriodSettingBySchoolIdAndDayOfWeek(schoolId, dayOfWeek)

    @GetMapping("/school/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllPeriodSettingsBySchoolId(@PathVariable schoolId: Long): List<PeriodSettingEntity> =
        periodSettingService.getAllPeriodSettingsBySchoolId(schoolId).toList()

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllPeriodSettings(): List<PeriodSettingEntity> =
        periodSettingService.getAllPeriodSettings()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updatePeriodSetting(
        @PathVariable id: Long,
        @RequestBody request: UpdatePeriodSettingRequest
    ): PeriodSettingEntity =
        periodSettingService.updatePeriodSetting(
            id = id,
            maxPeriod = request.maxPeriod
        )

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deletePeriodSetting(@PathVariable id: Long) =
        periodSettingService.deletePeriodSetting(id)

    @PutMapping("/school/{schoolId}/day/{dayOfWeek}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun createOrUpdatePeriodSetting(
        @PathVariable schoolId: Long,
        @PathVariable dayOfWeek: DayOfWeek,
        @RequestBody request: CreateOrUpdatePeriodSettingRequest
    ): PeriodSettingEntity =
        periodSettingService.createOrUpdatePeriodSetting(
            schoolId = schoolId,
            dayOfWeek = dayOfWeek,
            maxPeriod = request.maxPeriod
        )
}

data class CreatePeriodSettingRequest(
    val schoolId: Long,
    val dayOfWeek: DayOfWeek,
    val maxPeriod: Int
)

data class UpdatePeriodSettingRequest(
    val maxPeriod: Int
)

data class CreateOrUpdatePeriodSettingRequest(
    val maxPeriod: Int
)
