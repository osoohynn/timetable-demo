package kr.picktime.timetable.school.application.service

import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SchoolService(
    private val schoolRepository: SchoolRepository
) {
    @Transactional
    suspend fun createSchool(
        name: String,
        city: kr.picktime.timetable.school.domain.enums.City,
        schoolCode: String,
        password: String,
        isActive: Boolean = true
    ): SchoolEntity {
        if (schoolRepository.existsBySchoolCode(schoolCode)) {
            throw CustomException(SchoolErrorCode.SCHOOL_CODE_ALREADY_EXISTS)
        }

        val school = SchoolEntity(
            name = name,
            city = city,
            schoolCode = schoolCode,
            password = password,
            isActive = isActive
        )

        return schoolRepository.save(school)
    }

    @Transactional(readOnly = true)
    suspend fun getSchoolById(id: Long): SchoolEntity {
        return schoolRepository.findById(id)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getSchoolBySchoolCode(schoolCode: String): SchoolEntity {
        return schoolRepository.findBySchoolCode(schoolCode)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getAllSchools(): List<SchoolEntity> {
        return schoolRepository.findAll().toList()
    }

    @Transactional
    suspend fun updateSchool(
        id: Long,
        name: String? = null,
        city: kr.picktime.timetable.school.domain.enums.City? = null,
        password: String? = null,
        isActive: Boolean? = null
    ): SchoolEntity {
        val existingSchool = getSchoolById(id)

        val updatedSchool = existingSchool.copy(
            name = name ?: existingSchool.name,
            city = city ?: existingSchool.city,
            password = password ?: existingSchool.password,
            isActive = isActive ?: existingSchool.isActive
        )

        return schoolRepository.save(updatedSchool)
    }

    @Transactional
    suspend fun deleteSchool(id: Long) {
        if (!schoolRepository.existsById(id)) {
            throw CustomException(SchoolErrorCode.SCHOOL_NOT_FOUND)
        }
        schoolRepository.deleteById(id)
    }

    @Transactional
    suspend fun activateSchool(id: Long): SchoolEntity {
        return updateSchool(id, isActive = true)
    }

    @Transactional
    suspend fun deactivateSchool(id: Long): SchoolEntity {
        return updateSchool(id, isActive = false)
    }
}