package kr.picktime.timetable.period.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.period.domain.entity.PeriodEntity
import kr.picktime.timetable.period.domain.repository.PeriodRepository
import kr.picktime.timetable.period.exception.PeriodErrorCode
import kr.picktime.timetable.period.presentation.dto.request.CreatePeriodRequest
import kr.picktime.timetable.period.presentation.dto.response.PeriodResponse
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PeriodService(
    private val periodRepository: PeriodRepository,
    private val schoolRepository: SchoolRepository
) {
    @Transactional
    suspend fun createOrUpdatePeriod(schoolId: Long, request: CreatePeriodRequest): List<PeriodResponse> {
        val school = findSchoolBy(schoolId)
        if (school.weeklyClassHours != request.periods.size.toLong()) {
            throw CustomException(PeriodErrorCode.WEEKLY_CLASS_HOURS_MISMATCH)
        }

        upsertPeriod(schoolId, request)

        return findAllPeriodEntities(schoolId)
            .map { PeriodResponse.from(it) }
    }

    @Transactional(readOnly = true)
    suspend fun getPeriods(schoolId: Long): List<PeriodResponse> {
        return findAllPeriodEntities(schoolId).map { PeriodResponse.from(it) }
    }

    private suspend fun upsertPeriod(schoolId: Long, request: CreatePeriodRequest) {
        request.periods.forEach { period ->
            val existing = periodRepository.findBySchoolIdAndPeriod(
                schoolId,
                period.periodNumber
            )

            val entity = existing?.copy(
                startTime = period.startTime,
                endTime = period.endTime,
                dayOfWeek = period.dayOfWeek
            ) ?: PeriodEntity(
                schoolId = schoolId,
                period = period.periodNumber,
                startTime = period.startTime,
                endTime = period.endTime,
                dayOfWeek = period.dayOfWeek
            )

            periodRepository.save(entity)
        }
    }

    private suspend fun findAllPeriodEntities(schoolId: Long) =
        periodRepository.findAllBySchoolId(schoolId)
            .toList()

    private suspend fun findSchoolBy(schoolId: Long): SchoolEntity =
        schoolRepository.findById(schoolId)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
}
