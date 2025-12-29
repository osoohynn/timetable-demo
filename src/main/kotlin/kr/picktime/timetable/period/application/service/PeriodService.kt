package kr.picktime.timetable.period.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.period.domain.entity.PeriodEntity
import kr.picktime.timetable.period.domain.repository.PeriodTimeRepository
import kr.picktime.timetable.period.exception.PeriodErrorCode
import kr.picktime.timetable.period.presentation.dto.CreatePeriodRequest
import kr.picktime.timetable.period.presentation.dto.PeriodResponse
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PeriodService(
    private val periodTimeRepository: PeriodTimeRepository,
    private val schoolRepository: SchoolRepository
) {
    @Transactional
    suspend fun createPeriod(request: CreatePeriodRequest): List<PeriodResponse> {
        val school = getSchoolById(request.schoolId)

        val totalPeriods = request.periods.size.toLong()
        validateWeeklyClassHoursMatch(school.weeklyClassHours, totalPeriods)

        val saved = savePeriods(request)
        return saved.map { PeriodResponse.from(it) }
    }

    private fun validateWeeklyClassHoursMatch(weeklyClassHours: Long, totalPeriods: Long) {
        if (weeklyClassHours != totalPeriods) {
            throw CustomException(PeriodErrorCode.WEEKLY_CLASS_HOURS_MISMATCH)
        }
    }

    private suspend fun savePeriods(request: CreatePeriodRequest): List<PeriodEntity> {
        return request.periods.map { period ->
            val existingTime = periodTimeRepository.findBySchoolIdAndPeriod(
                request.schoolId,
                period.periodNumber.toInt()
            )
            if (existingTime != null) {
                periodTimeRepository.save(
                    existingTime.copy(
                        startTime = period.startTime,
                        endTime = period.endTime,
                        dayOfWeek = period.dayOfWeek
                    )
                )
            } else {
                periodTimeRepository.save(
                    createPeriodEntity(request, period)
                )
            }
        }
    }

    private fun createPeriodEntity(
        request: CreatePeriodRequest,
        period: CreatePeriodRequest.PeriodTimeRequest
    ): PeriodEntity {
        return PeriodEntity(
            schoolId = request.schoolId,
            period = period.periodNumber.toInt(),
            startTime = period.startTime,
            endTime = period.endTime,
            dayOfWeek = period.dayOfWeek
        )
    }

    private suspend fun getSchoolById(schoolId: Long): SchoolEntity {
        return schoolRepository.findById(schoolId)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
    }
}
