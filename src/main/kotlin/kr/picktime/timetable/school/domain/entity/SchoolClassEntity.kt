package kr.picktime.timetable.school.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "school_classes")
data class SchoolClassEntity(
    @Id
    val id: Long? = null,
    val schoolId: Long,
    val grade: Long,
    val classNumber: Long,
    val homeroomTeacherId: Long? = null,
)
