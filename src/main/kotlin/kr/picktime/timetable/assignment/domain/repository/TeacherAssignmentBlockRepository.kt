package kr.picktime.timetable.assignment.domain.repository

import kr.picktime.timetable.assignment.domain.entity.TeacherAssignmentBlockEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TeacherAssignmentBlockRepository : CoroutineCrudRepository<TeacherAssignmentBlockEntity, Long> {
    suspend fun findAllByAssignmentId(assignmentId: Long): List<TeacherAssignmentBlockEntity>
}
