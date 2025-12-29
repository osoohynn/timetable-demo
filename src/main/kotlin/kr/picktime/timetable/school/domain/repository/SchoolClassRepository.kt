package kr.picktime.timetable.school.domain.repository

import kr.picktime.timetable.school.domain.entity.SchoolClassEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface SchoolClassRepository : CoroutineCrudRepository<SchoolClassEntity, Long> {
}