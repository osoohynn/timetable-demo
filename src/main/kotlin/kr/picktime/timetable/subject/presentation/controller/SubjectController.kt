package kr.picktime.timetable.subject.presentation.controller

import kotlinx.coroutines.flow.toList
import kr.picktime.timetable.subject.application.service.SubjectService
import kr.picktime.timetable.subject.domain.entity.SubjectEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subjects")
class SubjectController(
    private val subjectService: SubjectService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createSubject(@RequestBody request: CreateSubjectRequest): SubjectEntity =
        subjectService.createSubject(
            name = request.name,
            shortName = request.shortName,
            schoolId = request.schoolId,
            isActive = request.isActive ?: true
        )

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getSubjectById(@PathVariable id: Long): SubjectEntity =
        subjectService.getSubjectById(id)

    @GetMapping("/school/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllSubjectsBySchoolId(@PathVariable schoolId: Long): List<SubjectEntity> =
        subjectService.getAllSubjectsBySchoolId(schoolId).toList()

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllSubjects(): List<SubjectEntity> =
        subjectService.getAllSubjects()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateSubject(
        @PathVariable id: Long,
        @RequestBody request: UpdateSubjectRequest
    ): SubjectEntity =
        subjectService.updateSubject(
            id = id,
            name = request.name,
            shortName = request.shortName,
            isActive = request.isActive
        )

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteSubject(@PathVariable id: Long) =
        subjectService.deleteSubject(id)

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    suspend fun activateSubject(@PathVariable id: Long): SubjectEntity =
        subjectService.activateSubject(id)

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    suspend fun deactivateSubject(@PathVariable id: Long): SubjectEntity =
        subjectService.deactivateSubject(id)
}

data class CreateSubjectRequest(
    val name: String,
    val shortName: String? = null,
    val schoolId: Long,
    val isActive: Boolean? = true
)

data class UpdateSubjectRequest(
    val name: String? = null,
    val shortName: String? = null,
    val isActive: Boolean? = null
)
