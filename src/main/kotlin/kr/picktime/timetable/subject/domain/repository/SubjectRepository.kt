package kr.picktime.timetable.subject.domain.repository

import kr.picktime.timetable.subject.domain.entity.SubjectEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface SubjectRepository : CoroutineCrudRepository<SubjectEntity, Long> {
}