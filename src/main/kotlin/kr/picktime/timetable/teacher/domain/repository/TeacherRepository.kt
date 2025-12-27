package kr.picktime.timetable.teacher.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.teacher.domain.entity.TeacherEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : CoroutineCrudRepository<TeacherEntity, Long> {
    fun findAllBySchoolId(schoolId: Long): Flow<TeacherEntity>
    suspend fun existsByIdAndSchoolId(id: Long, schoolId: Long): Boolean
}
