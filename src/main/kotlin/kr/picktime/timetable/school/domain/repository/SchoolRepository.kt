package kr.picktime.timetable.school.domain.repository

import kr.picktime.timetable.school.domain.entity.SchoolEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SchoolRepository : CoroutineCrudRepository<SchoolEntity, Long> {
    suspend fun findBySchoolCode(schoolCode: String): SchoolEntity?
    suspend fun existsBySchoolCode(schoolCode: String): Boolean
}
