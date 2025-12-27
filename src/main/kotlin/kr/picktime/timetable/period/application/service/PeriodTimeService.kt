package kr.picktime.timetable.period.application.service

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.global.exception.ErrorCode
import kr.picktime.timetable.period.domain.entity.PeriodTimeEntity
import kr.picktime.timetable.period.domain.repository.PeriodTimeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime

@Service
class PeriodTimeService(
    private val periodTimeRepository: PeriodTimeRepository
) {
    @Transactional
    suspend fun createPeriodTime(
        id: Long,
        schoolId: Long,
        period: Int,
        startTime: LocalTime,
        endTime: LocalTime
    ): PeriodTimeEntity {
        if (periodTimeRepository.existsById(id)) {
            throw CustomException(ErrorCode.PERIOD_TIME_ALREADY_EXISTS)
        }

        if (periodTimeRepository.existsBySchoolIdAndPeriod(schoolId, period)) {
            throw CustomException(ErrorCode.PERIOD_TIME_ALREADY_EXISTS)
        }

        if (period <= 0) {
            throw CustomException(ErrorCode.INVALID_PERIOD)
        }

        if (startTime >= endTime) {
            throw CustomException(ErrorCode.INVALID_TIME_RANGE)
        }

        val periodTime = PeriodTimeEntity(
            id = id,
            schoolId = schoolId,
            period = period,
            startTime = startTime,
            endTime = endTime
        )

        return periodTimeRepository.save(periodTime)
    }

    @Transactional(readOnly = true)
    suspend fun getPeriodTimeById(id: Long): PeriodTimeEntity {
        return periodTimeRepository.findById(id)
            ?: throw CustomException(ErrorCode.PERIOD_TIME_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getPeriodTimeBySchoolIdAndPeriod(schoolId: Long, period: Int): PeriodTimeEntity {
        return periodTimeRepository.findBySchoolIdAndPeriod(schoolId, period)
            ?: throw CustomException(ErrorCode.PERIOD_TIME_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getAllPeriodTimesBySchoolId(schoolId: Long): Flow<PeriodTimeEntity> {
        return periodTimeRepository.findAllBySchoolId(schoolId)
    }

    @Transactional(readOnly = true)
    suspend fun getAllPeriodTimes(): List<PeriodTimeEntity> {
        return periodTimeRepository.findAll().toList()
    }

    @Transactional
    suspend fun updatePeriodTime(
        id: Long,
        period: Int? = null,
        startTime: LocalTime? = null,
        endTime: LocalTime? = null
    ): PeriodTimeEntity {
        val existingPeriodTime = getPeriodTimeById(id)

        val newPeriod = period ?: existingPeriodTime.period
        val newStartTime = startTime ?: existingPeriodTime.startTime
        val newEndTime = endTime ?: existingPeriodTime.endTime

        if (newPeriod <= 0) {
            throw CustomException(ErrorCode.INVALID_PERIOD)
        }

        if (newStartTime >= newEndTime) {
            throw CustomException(ErrorCode.INVALID_TIME_RANGE)
        }

        if (period != null && period != existingPeriodTime.period) {
            if (periodTimeRepository.existsBySchoolIdAndPeriod(existingPeriodTime.schoolId, newPeriod)) {
                throw CustomException(ErrorCode.PERIOD_TIME_ALREADY_EXISTS)
            }
        }

        val updatedPeriodTime = existingPeriodTime.copy(
            period = newPeriod,
            startTime = newStartTime,
            endTime = newEndTime
        )

        return periodTimeRepository.save(updatedPeriodTime)
    }

    @Transactional
    suspend fun deletePeriodTime(id: Long) {
        if (!periodTimeRepository.existsById(id)) {
            throw CustomException(ErrorCode.PERIOD_TIME_NOT_FOUND)
        }
        periodTimeRepository.deleteById(id)
    }

    @Transactional
    suspend fun createOrUpdatePeriodTime(
        id: Long,
        schoolId: Long,
        period: Int,
        startTime: LocalTime,
        endTime: LocalTime
    ): PeriodTimeEntity {
        if (period <= 0) {
            throw CustomException(ErrorCode.INVALID_PERIOD)
        }

        if (startTime >= endTime) {
            throw CustomException(ErrorCode.INVALID_TIME_RANGE)
        }

        val existingPeriodTime = periodTimeRepository.findById(id)

        return if (existingPeriodTime != null) {
            periodTimeRepository.save(
                existingPeriodTime.copy(
                    period = period,
                    startTime = startTime,
                    endTime = endTime
                )
            )
        } else {
            createPeriodTime(id, schoolId, period, startTime, endTime)
        }
    }
}