package kr.picktime.timetable.subject.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.subject.application.service.SubjectService
import kr.picktime.timetable.subject.presentation.dto.request.CreateSubjectRequest
import kr.picktime.timetable.subject.presentation.dto.response.SubjectResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/schools/{schoolId}/subjects")
class SubjectController(
    private val subjectService: SubjectService
) {
    @PostMapping
    suspend fun createSubject(
        @Valid
        @PathVariable schoolId: Long,
        @RequestBody request: CreateSubjectRequest,
    ): SubjectResponse {
        return subjectService.createSubject(schoolId, request)
    }
}
