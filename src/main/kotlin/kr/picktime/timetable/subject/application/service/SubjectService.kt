package kr.picktime.timetable.subject.application.service

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.subject.domain.entity.SubjectEntity
import kr.picktime.timetable.subject.exception.SubjectErrorCode
import kr.picktime.timetable.subject.domain.repository.SubjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository
) {
    @Transactional
    suspend fun createSubject(
        name: String,
        shortName: String?,
        schoolId: Long,
        isActive: Boolean = true
    ): SubjectEntity {
        val subject = SubjectEntity(
            name = name,
            shortName = shortName,
            schoolId = schoolId,
            isActive = isActive
        )

        return subjectRepository.save(subject)
    }

    @Transactional(readOnly = true)
    suspend fun getSubjectById(id: Long): SubjectEntity {
        return subjectRepository.findById(id)
            ?: throw CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    suspend fun getAllSubjectsBySchoolId(schoolId: Long): Flow<SubjectEntity> {
        return subjectRepository.findAllBySchoolId(schoolId)
    }

    @Transactional(readOnly = true)
    suspend fun getAllSubjects(): List<SubjectEntity> {
        return subjectRepository.findAll().toList()
    }

    @Transactional
    suspend fun updateSubject(
        id: Long,
        name: String? = null,
        shortName: String? = null,
        isActive: Boolean? = null
    ): SubjectEntity {
        val existingSubject = getSubjectById(id)

        val updatedSubject = existingSubject.copy(
            name = name ?: existingSubject.name,
            shortName = shortName ?: existingSubject.shortName,
            isActive = isActive ?: existingSubject.isActive
        )

        return subjectRepository.save(updatedSubject)
    }

    @Transactional
    suspend fun deleteSubject(id: Long) {
        if (!subjectRepository.existsById(id)) {
            throw CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND)
        }
        subjectRepository.deleteById(id)
    }

    @Transactional
    suspend fun activateSubject(id: Long): SubjectEntity {
        return updateSubject(id, isActive = true)
    }

    @Transactional
    suspend fun deactivateSubject(id: Long): SubjectEntity {
        return updateSubject(id, isActive = false)
    }

    @Transactional(readOnly = true)
    suspend fun verifySubjectBelongsToSchool(id: Long, schoolId: Long): Boolean {
        return subjectRepository.existsByIdAndSchoolId(id, schoolId)
    }
}
