package kr.picktime.timetable.school.domain.entity

import kr.picktime.timetable.school.domain.enums.City
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("schools")
data class SchoolEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val isActive: Boolean,
    val city: City,
    val schoolCode: String,
    val password: String,
)
