package kr.picktime.timetable.specialroom.domain.repository

import kr.picktime.timetable.specialroom.domain.entity.SpecialRoomEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SpecialRoomRepository : CoroutineCrudRepository<SpecialRoomEntity, Long> {
}