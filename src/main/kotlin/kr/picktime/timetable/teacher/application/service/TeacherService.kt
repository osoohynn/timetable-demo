package kr.picktime.timetable.teacher.application.service

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.teacher.domain.entity.TeacherEntity
import kr.picktime.timetable.teacher.exception.TeacherErrorCode
import kr.picktime.timetable.teacher.domain.repository.TeacherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeacherService(
    private val teacherRepository: TeacherRepository
) {
    @Transactional
    suspend fun createTeacher(
        name: String,
        schoolId: Long,
        isActive: Boolean = true
    ): TeacherEntity {
        val teacher = TeacherEntity(
            name = name,
            schoolId = schoolId,
            isActive = isActive
        )

        return teacherRepository.save(teacher)
    }

    @Transactional(readOnly = true)
    suspend fun getTeacherById(id: Long): TeacherEntity {
        return teacherRepository.findById(id)
            ?: throw CustomException(TeacherErrorCode.TEACHER_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getAllTeachersBySchoolId(schoolId: Long): Flow<TeacherEntity> {
        return teacherRepository.findAllBySchoolId(schoolId)
    }

    @Transactional(readOnly = true)
    suspend fun getAllTeachers(): List<TeacherEntity> {
        return teacherRepository.findAll().toList()
    }

    @Transactional
    suspend fun updateTeacher(
        id: Long,
        name: String? = null,
        isActive: Boolean? = null
    ): TeacherEntity {
        val existingTeacher = getTeacherById(id)

        val updatedTeacher = existingTeacher.copy(
            name = name ?: existingTeacher.name,
            isActive = isActive ?: existingTeacher.isActive
        )

        return teacherRepository.save(updatedTeacher)
    }

    @Transactional
    suspend fun deleteTeacher(id: Long) {
        if (!teacherRepository.existsById(id)) {
            throw CustomException(TeacherErrorCode.TEACHER_NOT_FOUND)
        }
        teacherRepository.deleteById(id)
    }

    @Transactional
    suspend fun activateTeacher(id: Long): TeacherEntity {
        return updateTeacher(id, isActive = true)
    }

    @Transactional
    suspend fun deactivateTeacher(id: Long): TeacherEntity {
        return updateTeacher(id, isActive = false)
    }

    @Transactional(readOnly = true)
    suspend fun verifyTeacherBelongsToSchool(id: Long, schoolId: Long): Boolean {
        return teacherRepository.existsByIdAndSchoolId(id, schoolId)
    }
}
