package kr.picktime.timetable.assignment.application.service

import kr.picktime.timetable.assignment.domain.entity.TeacherAssignmentEntity
import kr.picktime.timetable.assignment.domain.repository.TeacherAssignmentRepository
import kr.picktime.timetable.assignment.presentation.dto.request.CreateTeacherAssignmentRequest
import org.springframework.stereotype.Service

@Service
class TeacherAssignmentService(
    private val teacherAssignmentRepository: TeacherAssignmentRepository,
) {
    suspend fun createAssignments(request: CreateTeacherAssignmentRequest) {
        request.assignments.forEach { assignment ->
            val entity = TeacherAssignmentEntity(
                teacherId = request.teacherId,
                classId = assignment.classId,
                subjectId = assignment.subjectId,
                hoursPerWeek = assignment.hoursPerWeek,
                isBlock = assignment.isBlock,
                blockSize = assignment.blockSize,
                blockCount = assignment.blockCount
            )
            teacherAssignmentRepository.save(entity)
        }
    }

    suspend fun getAssignmentsByTeacher(teacherId: Long): List<TeacherAssignmentEntity> {
        return teacherAssignmentRepository.findAllByTeacherId(teacherId)
    }
}
