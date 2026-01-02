package kr.picktime.timetable.subject.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.subject.domain.entity.SubjectEntity
import kr.picktime.timetable.subject.domain.repository.SubjectRepository
import kr.picktime.timetable.subject.presentation.dto.request.CreateSubjectRequest
import kr.picktime.timetable.subject.presentation.dto.response.SubjectResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val schoolRepository: SchoolRepository,
) {
    @Transactional
    suspend fun createSubject(schoolId: Long, request: CreateSubjectRequest): SubjectResponse {
        val school = findSchoolEntity(schoolId)
        val saved = createSubjectEntity(school.id!!, request)
        return SubjectResponse.from(saved)
    }

    private suspend fun createSubjectEntity(
        schoolId: Long,
        request: CreateSubjectRequest
    ): SubjectEntity {
        val subject = SubjectEntity(
            schoolId = schoolId,
            name = request.name,
            shortName = request.shortName,
        )
        return subjectRepository.save(subject)
    }

    private suspend fun findSchoolEntity(schoolId: Long) =
        schoolRepository.findById(schoolId) ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
}