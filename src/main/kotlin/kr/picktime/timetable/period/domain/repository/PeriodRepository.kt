package kr.picktime.timetable.period.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.period.domain.entity.PeriodEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PeriodRepository : CoroutineCrudRepository<PeriodEntity, Long> {
    fun findAllBySchoolId(schoolId: Long): Flow<PeriodEntity>
    suspend fun findBySchoolIdAndPeriod(schoolId: Long, period: Long): PeriodEntity?
    suspend fun existsBySchoolIdAndPeriod(schoolId: Long, period: Long): Boolean
}
