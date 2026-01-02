package kr.picktime.timetable.subject.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "subjects")
data class SubjectEntity(
    @Id
    val id: Long? = null,
    val schoolId: Long,
    val name: String,
    val shortName: String,
)

// TODO 과목 특별실 다대다 해야할 거 같고
// TODO 반 과목 연결 해야할려나 일단 보류