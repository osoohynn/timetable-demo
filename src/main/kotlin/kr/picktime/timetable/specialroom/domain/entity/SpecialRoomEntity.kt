package kr.picktime.timetable.specialroom.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("special_rooms")
data class SpecialRoomEntity(
    @Id
    val id: Long? = null,
    val schoolId: Long,
    val name: String, // unique
)
