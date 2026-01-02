package kr.picktime.timetable.teacher.application.service

import kr.picktime.timetable.teacher.domain.entity.TeacherRequestEntity
import kr.picktime.timetable.teacher.domain.repository.TeacherRequestRepository
import kr.picktime.timetable.teacher.presentation.dto.request.CreateTeacherRequestRequest
import org.springframework.stereotype.Service

@Service
class TeacherRequestService(
    private val teacherRequestRepository: TeacherRequestRepository,
) {
    suspend fun createRequests(teacherId: Long, request: CreateTeacherRequestRequest) {
        request.requests.forEach { item ->
            val entity = TeacherRequestEntity(
                teacherId = teacherId,
                dayOfWeek = item.dayOfWeek,
                periodNumber = item.periodNumber,
                description = item.description
            )
            teacherRequestRepository.save(entity)
        }
    }

    suspend fun getRequestsByTeacher(teacherId: Long): List<TeacherRequestEntity> {
        return teacherRequestRepository.findAllByTeacherId(teacherId)
    }
}
