package kr.picktime.timetable.teacher.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.teacher.domain.entity.TeacherAvailabilityEntity
import kr.picktime.timetable.teacher.domain.entity.TeacherEntity
import kr.picktime.timetable.teacher.domain.enum.TeacherType
import kr.picktime.timetable.teacher.domain.repository.TeacherAvailabilityRepository
import kr.picktime.timetable.teacher.domain.repository.TeacherRepository
import kr.picktime.timetable.teacher.presentation.dto.request.CreateTeacherRequest
import kr.picktime.timetable.teacher.presentation.dto.response.TeacherResponse
import org.springframework.stereotype.Service

@Service
class TeacherService(
    private val teacherRepository: TeacherRepository,
    private val teacherAvailabilityRepository: TeacherAvailabilityRepository,
    private val schoolRepository: SchoolRepository,
) {
    suspend fun createTeacher(schoolId: Long, request: CreateTeacherRequest): TeacherResponse {
        val school = findSchoolEntityBy(schoolId)
        val teacher = createTeacherEntity(school.id!!, request)
        val saved = teacherRepository.save(teacher)
        if (teacher.type != TeacherType.REGULAR) {
            request.availableTimes?.forEach {
                val teacherAvailability = createTeacherAvailabilityEntity(saved.id!!, it)
                teacherAvailabilityRepository.save(teacherAvailability)
            }
        }
        return TeacherResponse.from(saved)
    }

    private fun createTeacherAvailabilityEntity(
        teacherId: Long,
        availableTime: CreateTeacherRequest.CreateTeacherAvailabilityRequest
    ): TeacherAvailabilityEntity {
        return TeacherAvailabilityEntity(
            teacherId = teacherId,
            dayOfWeek = availableTime.dayOfWeek,
            startPeriod = availableTime.startPeriod,
            endPeriod = availableTime.endPeriod
        )
    }

    private fun createTeacherEntity(
        schoolId: Long,
        request: CreateTeacherRequest
    ): TeacherEntity {
        return TeacherEntity(
            schoolId = schoolId,
            name = request.name,
            type = request.type,
        )
    }

    suspend fun getTeachers(schoolId: Long): List<TeacherResponse> {
        return teacherRepository.findAllBySchoolId(schoolId).map { TeacherResponse.from(it) }
    }

    private suspend fun findSchoolEntityBy(schoolId: Long): SchoolEntity {
        return schoolRepository.findById(schoolId)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
    }
}
