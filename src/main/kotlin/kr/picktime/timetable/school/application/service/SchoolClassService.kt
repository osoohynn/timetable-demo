package kr.picktime.timetable.school.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolClassEntity
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.repository.SchoolClassRepository
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.school.presentation.dto.request.CreateSchoolClassRequest
import org.springframework.stereotype.Service

@Service
class SchoolClassService(
    private val schoolClassRepository: SchoolClassRepository,
    private val schoolRepository: SchoolRepository,
) {
    suspend fun createClass(schoolId: Long, request: CreateSchoolClassRequest) {
        val school = findSchoolEntityBy(schoolId)

        // TODO 중복체크
        request.classes.forEach {
            for (classNumber in 1 .. it.classCount) {
                schoolClassRepository.save(SchoolClassEntity(
                    schoolId = school.id!!,
                    grade = it.grade,
                    classNumber = classNumber,
                ))
            }
        }
    }

    private suspend fun findSchoolEntityBy(schoolId: Long): SchoolEntity {
        return (schoolRepository.findById(schoolId)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND))
    }
}