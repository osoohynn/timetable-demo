package kr.picktime.timetable.period.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.period.domain.entity.PeriodTimeEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PeriodTimeRepository : CoroutineCrudRepository<PeriodTimeEntity, Long> {
    fun findAllBySchoolId(schoolId: Long): Flow<PeriodTimeEntity>
    suspend fun findBySchoolIdAndPeriod(schoolId: Long, period: Int): PeriodTimeEntity?
    suspend fun existsBySchoolIdAndPeriod(schoolId: Long, period: Int): Boolean
}
