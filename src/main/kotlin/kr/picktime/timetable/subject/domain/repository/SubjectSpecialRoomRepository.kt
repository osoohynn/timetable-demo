package kr.picktime.timetable.subject.domain.repository

import kr.picktime.timetable.subject.domain.entity.SubjectSpecialRoomEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface SubjectSpecialRoomRepository : CoroutineCrudRepository<SubjectSpecialRoomEntity, Long> {
}