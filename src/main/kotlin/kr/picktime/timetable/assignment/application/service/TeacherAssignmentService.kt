package kr.picktime.timetable.assignment.application.service

import kr.picktime.timetable.assignment.domain.entity.TeacherAssignmentBlockEntity
import kr.picktime.timetable.assignment.domain.entity.TeacherAssignmentEntity
import kr.picktime.timetable.assignment.domain.repository.TeacherAssignmentBlockRepository
import kr.picktime.timetable.assignment.domain.repository.TeacherAssignmentRepository
import kr.picktime.timetable.assignment.presentation.dto.request.CreateTeacherAssignmentRequest
import org.springframework.stereotype.Service

@Service
class TeacherAssignmentService(
    private val teacherAssignmentRepository: TeacherAssignmentRepository,
    private val teacherAssignmentBlockRepository: TeacherAssignmentBlockRepository,
) {
    suspend fun createAssignments(request: CreateTeacherAssignmentRequest) {
        request.assignments.forEach { assignment ->
            val entity = TeacherAssignmentEntity(
                teacherId = request.teacherId,
                classId = assignment.classId,
                subjectId = assignment.subjectId,
                hoursPerWeek = assignment.hoursPerWeek,
                isBlock = assignment.isBlock,
            )
            val saved = teacherAssignmentRepository.save(entity)

            // 블록 수업인 경우 블록 크기 저장
            if (assignment.isBlock && assignment.blockSizes != null) {
                assignment.blockSizes.forEachIndexed { index, blockSize ->
                    val blockEntity = TeacherAssignmentBlockEntity(
                        assignmentId = saved.id!!,
                        blockSize = blockSize,
                        blockOrder = index.toLong() + 1
                    )
                    teacherAssignmentBlockRepository.save(blockEntity)
                }
            }
        }
    }

    suspend fun getAssignmentsByTeacher(teacherId: Long): List<TeacherAssignmentEntity> {
        return teacherAssignmentRepository.findAllByTeacherId(teacherId)
    }
}
