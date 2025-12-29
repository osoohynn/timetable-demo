package kr.picktime.timetable.period.application.service

import kotlinx.coroutines.flow.toList
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.period.domain.entity.PeriodSettingEntity
import kr.picktime.timetable.period.domain.entity.PeriodTimeEntity
import kr.picktime.timetable.period.domain.repository.PeriodSettingRepository
import kr.picktime.timetable.period.domain.repository.PeriodTimeRepository
import kr.picktime.timetable.period.exception.PeriodErrorCode
import kr.picktime.timetable.period.presentation.dto.CreatePeriodRequest
import kr.picktime.timetable.period.presentation.dto.DayPeriodResponse
import kr.picktime.timetable.period.presentation.dto.PeriodResponse
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek

@Service
class PeriodService(
    private val periodSettingRepository: PeriodSettingRepository,
    private val periodTimeRepository: PeriodTimeRepository,
    private val schoolRepository: SchoolRepository
) {
    @Transactional
    suspend fun createPeriod(request: CreatePeriodRequest): PeriodResponse {
        if (request.periods.isEmpty()) {
            throw CustomException(PeriodErrorCode.INVALID_INPUT)
        }

        // 학교 정보 조회
        val school = schoolRepository.findById(request.schoolId)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)

        // 유효성 검증
        request.periods.forEach { period ->
            if (period.periodNumber <= 0) {
                throw CustomException(PeriodErrorCode.INVALID_PERIOD)
            }
            if (period.startTime >= period.endTime) {
                throw CustomException(PeriodErrorCode.INVALID_TIME_RANGE)
            }
        }


        // 주간 시수 검증
        val totalPeriods = request.periods.size.toLong()
        if (totalPeriods != school.weeklyClassHours) {
            throw CustomException(PeriodErrorCode.WEEKLY_CLASS_HOURS_MISMATCH)
        }

        // 요일별로 그룹화
        val periodsByDay = request.periods.groupBy { it.dayOfWeek }

        val periodSettings = mutableListOf<PeriodSettingEntity>()
        val periodTimes = mutableListOf<PeriodTimeEntity>()

        periodsByDay.forEach { (dayOfWeek, periods) ->
            val maxPeriod = periods.maxOf { it.periodNumber }

            // PeriodSetting upsert
            val existingSetting = periodSettingRepository.findBySchoolIdAndDayOfWeek(request.schoolId, dayOfWeek)
            val periodSetting = if (existingSetting != null) {
                periodSettingRepository.save(
                    existingSetting.copy(maxPeriod = maxPeriod.toInt())
                )
            } else {
                periodSettingRepository.save(
                    PeriodSettingEntity(
                        schoolId = request.schoolId,
                        dayOfWeek = dayOfWeek,
                        maxPeriod = maxPeriod.toInt()
                    )
                )
            }
            periodSettings.add(periodSetting)

            // PeriodTime upsert
            periods.forEach { period ->
                val existingTime = periodTimeRepository.findBySchoolIdAndPeriod(
                    request.schoolId,
                    period.periodNumber.toInt()
                )
                val periodTime = if (existingTime != null) {
                    periodTimeRepository.save(
                        existingTime.copy(
                            startTime = period.startTime,
                            endTime = period.endTime
                        )
                    )
                } else {
                    periodTimeRepository.save(
                        PeriodTimeEntity(
                            schoolId = request.schoolId,
                            period = period.periodNumber.toInt(),
                            startTime = period.startTime,
                            endTime = period.endTime
                        )
                    )
                }
                periodTimes.add(periodTime)
            }
        }

        return PeriodResponse(
            periodSettings = periodSettings,
            periodTimes = periodTimes
        )
    }

    @Transactional(readOnly = true)
    suspend fun getPeriodsBySchoolId(schoolId: Long): PeriodResponse {
        val periodSettings = periodSettingRepository.findAllBySchoolId(schoolId).toList()
        val periodTimes = periodTimeRepository.findAllBySchoolId(schoolId).toList()

        return PeriodResponse(
            periodSettings = periodSettings,
            periodTimes = periodTimes
        )
    }

    @Transactional(readOnly = true)
    suspend fun getPeriodsBySchoolIdAndDayOfWeek(schoolId: Long, dayOfWeek: DayOfWeek): DayPeriodResponse {
        val periodSetting = periodSettingRepository.findBySchoolIdAndDayOfWeek(schoolId, dayOfWeek)
            ?: throw CustomException(PeriodErrorCode.PERIOD_SETTING_NOT_FOUND)

        val periodTimes = periodTimeRepository.findAllBySchoolId(schoolId).toList()

        return DayPeriodResponse(
            periodSetting = periodSetting,
            periodTimes = periodTimes
        )
    }

    @Transactional
    suspend fun updatePeriod(schoolId: Long, request: CreatePeriodRequest): PeriodResponse {
        // 새로운 데이터로 upsert (createPeriod가 이미 upsert 로직 포함)
        return createPeriod(request.copy(schoolId = schoolId))
    }
}
