package kr.picktime.timetable.period.application.service

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.period.domain.entity.PeriodSettingEntity
import kr.picktime.timetable.period.exception.PeriodErrorCode
import kr.picktime.timetable.period.domain.repository.PeriodSettingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek

@Service
class PeriodSettingService(
    private val periodSettingRepository: PeriodSettingRepository
) {
    @Transactional
    suspend fun createPeriodSetting(
        schoolId: Long,
        dayOfWeek: DayOfWeek,
        maxPeriod: Int
    ): PeriodSettingEntity {
        if (periodSettingRepository.existsBySchoolIdAndDayOfWeek(schoolId, dayOfWeek)) {
            throw CustomException(PeriodErrorCode.PERIOD_SETTING_ALREADY_EXISTS)
        }

        if (maxPeriod <= 0) {
            throw CustomException(PeriodErrorCode.INVALID_MAX_PERIOD)
        }

        val periodSetting = PeriodSettingEntity(
            schoolId = schoolId,
            dayOfWeek = dayOfWeek,
            maxPeriod = maxPeriod
        )

        return periodSettingRepository.save(periodSetting)
    }

    @Transactional(readOnly = true)
    suspend fun getPeriodSettingById(id: Long): PeriodSettingEntity {
        return periodSettingRepository.findById(id)
            ?: throw CustomException(PeriodErrorCode.PERIOD_SETTING_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getPeriodSettingBySchoolIdAndDayOfWeek(schoolId: Long, dayOfWeek: DayOfWeek): PeriodSettingEntity {
        return periodSettingRepository.findBySchoolIdAndDayOfWeek(schoolId, dayOfWeek)
            ?: throw CustomException(PeriodErrorCode.PERIOD_SETTING_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getAllPeriodSettingsBySchoolId(schoolId: Long): Flow<PeriodSettingEntity> {
        return periodSettingRepository.findAllBySchoolId(schoolId)
    }

    @Transactional(readOnly = true)
    suspend fun getAllPeriodSettings(): List<PeriodSettingEntity> {
        return periodSettingRepository.findAll().toList()
    }

    @Transactional
    suspend fun updatePeriodSetting(
        id: Long,
        maxPeriod: Int? = null
    ): PeriodSettingEntity {
        val existingSetting = getPeriodSettingById(id)

        val newMaxPeriod = maxPeriod ?: existingSetting.maxPeriod

        if (newMaxPeriod <= 0) {
            throw CustomException(PeriodErrorCode.INVALID_MAX_PERIOD)
        }

        val updatedSetting = existingSetting.copy(
            maxPeriod = newMaxPeriod
        )

        return periodSettingRepository.save(updatedSetting)
    }

    @Transactional
    suspend fun deletePeriodSetting(id: Long) {
        if (!periodSettingRepository.existsById(id)) {
            throw CustomException(PeriodErrorCode.PERIOD_SETTING_NOT_FOUND)
        }
        periodSettingRepository.deleteById(id)
    }

    @Transactional
    suspend fun createOrUpdatePeriodSetting(
        schoolId: Long,
        dayOfWeek: DayOfWeek,
        maxPeriod: Int
    ): PeriodSettingEntity {
        if (maxPeriod <= 0) {
            throw CustomException(PeriodErrorCode.INVALID_MAX_PERIOD)
        }

        val existingSetting = periodSettingRepository.findBySchoolIdAndDayOfWeek(schoolId, dayOfWeek)

        return if (existingSetting != null) {
            periodSettingRepository.save(existingSetting.copy(maxPeriod = maxPeriod))
        } else {
            createPeriodSetting(schoolId, dayOfWeek, maxPeriod)
        }
    }
}