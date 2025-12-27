package kr.picktime.timetable.period.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.period.domain.entity.PeriodSettingEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.DayOfWeek

@Repository
interface PeriodSettingRepository : CoroutineCrudRepository<PeriodSettingEntity, Long> {
    fun findAllBySchoolId(schoolId: Long): Flow<PeriodSettingEntity>
    suspend fun findBySchoolIdAndDayOfWeek(schoolId: Long, dayOfWeek: DayOfWeek): PeriodSettingEntity?
    suspend fun existsBySchoolIdAndDayOfWeek(schoolId: Long, dayOfWeek: DayOfWeek): Boolean
}
