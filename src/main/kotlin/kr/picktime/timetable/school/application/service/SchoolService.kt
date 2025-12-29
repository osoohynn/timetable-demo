package kr.picktime.timetable.school.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.school.presentation.dto.request.CreateSchoolRequest
import kr.picktime.timetable.school.presentation.dto.response.SchoolResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SchoolService(
    private val schoolRepository: SchoolRepository
) {
    @Transactional
    suspend fun createSchool(request: CreateSchoolRequest): SchoolResponse {
        validateSchoolCodeNotExists(request.schoolCode)
        val school = createSchoolEntity(request)
        val saved = schoolRepository.save(school)
        return SchoolResponse.from(saved)
    }

    private suspend fun validateSchoolCodeNotExists(schoolCode: String) {
        if (schoolRepository.existsBySchoolCode(schoolCode)) {
            throw CustomException(SchoolErrorCode.SCHOOL_CODE_ALREADY_EXISTS)
        }
    }

    private fun createSchoolEntity(request: CreateSchoolRequest): SchoolEntity {
        return SchoolEntity(
            name = request.name,
            city = request.city,
            schoolCode = request.schoolCode,
            password = request.password,
            isActive = request.isActive ?: true,
            weeklyClassHours = request.weeklyClassHours
        )
    }
}