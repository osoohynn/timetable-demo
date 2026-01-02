package kr.picktime.timetable.assignment.domain.repository

import kr.picktime.timetable.assignment.domain.entity.TeacherAssignmentEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherAssignmentRepository : CoroutineCrudRepository<TeacherAssignmentEntity, Long> {
    suspend fun findAllByTeacherId(teacherId: Long): List<TeacherAssignmentEntity>
}
