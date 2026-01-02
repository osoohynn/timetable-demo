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
import org.springframework.stereotype.Service

@Service
class TeacherService(
    private val teacherRepository: TeacherRepository,
    private val teacherAvailabilityRepository: TeacherAvailabilityRepository,
    private val schoolRepository: SchoolRepository,
) {
    suspend fun createTeacher(schoolId: Long, request: CreateTeacherRequest) {
        val school = findSchoolEntityBy(schoolId)
        val teacher = TeacherEntity(
            schoolId = school.id!!,
            name = request.name,
            type = request.type,
        )
        teacherRepository.save(teacher)
        if (teacher.type != TeacherType.REGULAR) {
            request.availableTimes?.forEach{
                val teacherAvailability = TeacherAvailabilityEntity(
                    teacherId = teacher.id!!,
                    dayOfWeek = it.dayOfWeek,
                    startPeriod = it.startPeriod,
                    endPeriod = it.endPeriod
                )
                teacherAvailabilityRepository.save(teacherAvailability)
            }
        }
    }

    private suspend fun findSchoolEntityBy(schoolId: Long): SchoolEntity {
        return schoolRepository.findById(schoolId)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
    }
}
