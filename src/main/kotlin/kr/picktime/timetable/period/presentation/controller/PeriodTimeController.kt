package kr.picktime.timetable.period.presentation.controller

import kotlinx.coroutines.flow.toList
import kr.picktime.timetable.period.application.service.PeriodTimeService
import kr.picktime.timetable.period.domain.entity.PeriodTimeEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalTime

@RestController
@RequestMapping("/api/period-times")
class PeriodTimeController(
    private val periodTimeService: PeriodTimeService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createPeriodTime(@RequestBody request: CreatePeriodTimeRequest): PeriodTimeEntity =
        periodTimeService.createPeriodTime(
            id = request.id,
            schoolId = request.schoolId,
            period = request.period,
            startTime = request.startTime,
            endTime = request.endTime
        )

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getPeriodTimeById(@PathVariable id: Long): PeriodTimeEntity =
        periodTimeService.getPeriodTimeById(id)

    @GetMapping("/school/{schoolId}/period/{period}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getPeriodTimeBySchoolIdAndPeriod(
        @PathVariable schoolId: Long,
        @PathVariable period: Int
    ): PeriodTimeEntity =
        periodTimeService.getPeriodTimeBySchoolIdAndPeriod(schoolId, period)

    @GetMapping("/school/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllPeriodTimesBySchoolId(@PathVariable schoolId: Long): List<PeriodTimeEntity> =
        periodTimeService.getAllPeriodTimesBySchoolId(schoolId).toList()

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllPeriodTimes(): List<PeriodTimeEntity> =
        periodTimeService.getAllPeriodTimes()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updatePeriodTime(
        @PathVariable id: Long,
        @RequestBody request: UpdatePeriodTimeRequest
    ): PeriodTimeEntity =
        periodTimeService.updatePeriodTime(
            id = id,
            period = request.period,
            startTime = request.startTime,
            endTime = request.endTime
        )

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deletePeriodTime(@PathVariable id: Long) =
        periodTimeService.deletePeriodTime(id)

    @PutMapping("/{id}/upsert")
    @ResponseStatus(HttpStatus.OK)
    suspend fun createOrUpdatePeriodTime(
        @PathVariable id: Long,
        @RequestBody request: CreateOrUpdatePeriodTimeRequest
    ): PeriodTimeEntity =
        periodTimeService.createOrUpdatePeriodTime(
            id = id,
            schoolId = request.schoolId,
            period = request.period,
            startTime = request.startTime,
            endTime = request.endTime
        )
}

data class CreatePeriodTimeRequest(
    val id: Long,
    val schoolId: Long,
    val period: Int,
    val startTime: LocalTime,
    val endTime: LocalTime
)

data class UpdatePeriodTimeRequest(
    val period: Int? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null
)

data class CreateOrUpdatePeriodTimeRequest(
    val schoolId: Long,
    val period: Int,
    val startTime: LocalTime,
    val endTime: LocalTime
)
