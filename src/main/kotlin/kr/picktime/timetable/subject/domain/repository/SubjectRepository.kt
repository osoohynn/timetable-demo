package kr.picktime.timetable.subject.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.picktime.timetable.subject.domain.entity.SubjectEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : CoroutineCrudRepository<SubjectEntity, Long> {
    fun findAllBySchoolId(schoolId: Long): Flow<SubjectEntity>
    suspend fun existsByIdAndSchoolId(id: Long, schoolId: Long): Boolean
}
