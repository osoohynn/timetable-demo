package kr.picktime.timetable.teacher.domain.repository

import kr.picktime.timetable.teacher.domain.entity.TeacherRequestEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherRequestRepository : CoroutineCrudRepository<TeacherRequestEntity, Long> {
    suspend fun findAllByTeacherId(teacherId: Long): List<TeacherRequestEntity>
}
