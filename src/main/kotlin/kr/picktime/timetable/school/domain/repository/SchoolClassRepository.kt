package kr.picktime.timetable.school.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.school.domain.entity.SchoolClassEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SchoolClassRepository : CoroutineCrudRepository<SchoolClassEntity, Long> {
    fun findAllBySchoolId(schoolId: Long): Flow<SchoolClassEntity>
    suspend fun existsByIdAndSchoolId(id: Long, schoolId: Long): Boolean
    suspend fun existsBySchoolIdAndGradeAndClassNumber(schoolId: Long, grade: Int, classNumber: Int): Boolean
}
