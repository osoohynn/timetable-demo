package kr.picktime.timetable.subject.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.subject.domain.entity.SubjectEntity
import kr.picktime.timetable.subject.domain.entity.SubjectSpecialRoomEntity
import kr.picktime.timetable.subject.domain.repository.SubjectRepository
import kr.picktime.timetable.subject.domain.repository.SubjectSpecialRoomRepository
import kr.picktime.timetable.subject.presentation.dto.request.CreateSubjectRequest
import kr.picktime.timetable.subject.presentation.dto.response.SubjectResponse
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val schoolRepository: SchoolRepository,
    private val subjectSpecialRoomRepository: SubjectSpecialRoomRepository
) {
    suspend fun createSubject(schoolId: Long, request: CreateSubjectRequest): SubjectResponse {
        val school = findSchoolEntityBy(schoolId)
        val subject = createSubjectEntity(school.id!!, request)
        val saved = subjectRepository.save(subject)
        request.specialRoomIds.forEach { specialRoomId ->
            val specialRoom = createSpecialRoomEntity(saved.id!!, specialRoomId)
            subjectSpecialRoomRepository.save(
                specialRoom
            )
        }
        return SubjectResponse.from(saved)
    }

    private fun createSpecialRoomEntity(
        subjectId: Long,
        specialRoomId: Long
    ): SubjectSpecialRoomEntity {
        return SubjectSpecialRoomEntity(
            subjectId = subjectId,
            specialRoomId = specialRoomId
        )
    }

    private fun createSubjectEntity(
        schoolId: Long,
        request: CreateSubjectRequest
    ): SubjectEntity {
        return SubjectEntity(
            schoolId = schoolId,
            name = request.name,
            shortName = request.shortName,
        )
    }

    private suspend fun findSchoolEntityBy(schoolId: Long): SchoolEntity {
        return schoolRepository.findById(schoolId) ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
    }
}