package kr.picktime.timetable.subject.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("subjects")
data class SubjectEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val shortName: String?,
    val isActive: Boolean,
    val schoolId: Long,
)
