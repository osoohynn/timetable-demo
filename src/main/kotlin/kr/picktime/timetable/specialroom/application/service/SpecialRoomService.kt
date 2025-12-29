package kr.picktime.timetable.specialroom.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.specialroom.domain.entity.SpecialRoomEntity
import kr.picktime.timetable.specialroom.domain.repository.SpecialRoomRepository
import kr.picktime.timetable.specialroom.presentation.dto.request.CreateSpecialRoomRequest
import kr.picktime.timetable.specialroom.presentation.dto.response.SpecialRoomResponse
import org.springframework.stereotype.Service

@Service
class SpecialRoomService(
    private val specialRoomRepository: SpecialRoomRepository,
    private val schoolRepository: SchoolRepository,
) {
    suspend fun createSpecialRoom(schoolId: Long, request: CreateSpecialRoomRequest): SpecialRoomResponse {
        val school = findSchoolBy(schoolId)
        val specialRoom = createSpecialRoomEntity(school.id!!, request)
        val saved = specialRoomRepository.save(specialRoom)
        return SpecialRoomResponse.from(saved)
    }

    private fun createSpecialRoomEntity(
        schoolId: Long,
        request: CreateSpecialRoomRequest
    ) = SpecialRoomEntity(
        schoolId = schoolId,
        name = request.name,
    )

    private suspend fun findSchoolBy(schoolId: Long): SchoolEntity =
        schoolRepository.findById(schoolId)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
}