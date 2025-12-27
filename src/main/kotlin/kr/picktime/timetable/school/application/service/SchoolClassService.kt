package kr.picktime.timetable.school.application.service

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolClassEntity
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.school.domain.repository.SchoolClassRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SchoolClassService(
    private val schoolClassRepository: SchoolClassRepository
) {
    @Transactional
    suspend fun createSchoolClass(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        isActive: Boolean = true
    ): SchoolClassEntity {
        if (schoolClassRepository.existsBySchoolIdAndGradeAndClassNumber(schoolId, grade, classNumber)) {
            throw CustomException(SchoolErrorCode.SCHOOL_CLASS_ALREADY_EXISTS)
        }

        val schoolClass = SchoolClassEntity(
            schoolId = schoolId,
            grade = grade,
            classNumber = classNumber,
            isActive = isActive
        )

        return schoolClassRepository.save(schoolClass)
    }

    @Transactional(readOnly = true)
    suspend fun getSchoolClassById(id: Long): SchoolClassEntity {
        return schoolClassRepository.findById(id)
            ?: throw CustomException(SchoolErrorCode.SCHOOL_CLASS_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getAllSchoolClassesBySchoolId(schoolId: Long): Flow<SchoolClassEntity> {
        return schoolClassRepository.findAllBySchoolId(schoolId)
    }

    @Transactional(readOnly = true)
    suspend fun getAllSchoolClasses(): List<SchoolClassEntity> {
        return schoolClassRepository.findAll().toList()
    }

    @Transactional
    suspend fun updateSchoolClass(
        id: Long,
        grade: Int? = null,
        classNumber: Int? = null,
        isActive: Boolean? = null
    ): SchoolClassEntity {
        val existingClass = getSchoolClassById(id)

        val newGrade = grade ?: existingClass.grade
        val newClassNumber = classNumber ?: existingClass.classNumber

        if ((grade != null || classNumber != null) &&
            schoolClassRepository.existsBySchoolIdAndGradeAndClassNumber(
                existingClass.schoolId,
                newGrade,
                newClassNumber
            ) && (newGrade != existingClass.grade || newClassNumber != existingClass.classNumber)
        ) {
            throw CustomException(SchoolErrorCode.SCHOOL_CLASS_ALREADY_EXISTS)
        }

        val updatedClass = existingClass.copy(
            grade = newGrade,
            classNumber = newClassNumber,
            isActive = isActive ?: existingClass.isActive
        )

        return schoolClassRepository.save(updatedClass)
    }

    @Transactional
    suspend fun deleteSchoolClass(id: Long) {
        if (!schoolClassRepository.existsById(id)) {
            throw CustomException(SchoolErrorCode.SCHOOL_CLASS_NOT_FOUND)
        }
        schoolClassRepository.deleteById(id)
    }

    @Transactional
    suspend fun activateSchoolClass(id: Long): SchoolClassEntity {
        return updateSchoolClass(id, isActive = true)
    }

    @Transactional
    suspend fun deactivateSchoolClass(id: Long): SchoolClassEntity {
        return updateSchoolClass(id, isActive = false)
    }

    @Transactional(readOnly = true)
    suspend fun verifySchoolClassBelongsToSchool(id: Long, schoolId: Long): Boolean {
        return schoolClassRepository.existsByIdAndSchoolId(id, schoolId)
    }
}
