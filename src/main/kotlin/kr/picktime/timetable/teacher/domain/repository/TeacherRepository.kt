package kr.picktime.timetable.teacher.domain.repository

import kr.picktime.timetable.teacher.domain.entity.TeacherEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TeacherRepository : CoroutineCrudRepository<TeacherEntity, Long> {
    suspend fun findAllBySchoolId(schoolId: Long): List<TeacherEntity>
}