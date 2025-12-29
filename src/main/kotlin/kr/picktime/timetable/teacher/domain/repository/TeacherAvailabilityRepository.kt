package kr.picktime.timetable.teacher.domain.repository

import kr.picktime.timetable.teacher.domain.entity.TeacherAvailabilityEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TeacherAvailabilityRepository : CoroutineCrudRepository<TeacherAvailabilityEntity, Long> {
}