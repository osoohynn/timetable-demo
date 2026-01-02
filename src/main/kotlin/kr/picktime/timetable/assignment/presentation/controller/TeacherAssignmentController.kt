package kr.picktime.timetable.assignment.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.assignment.application.service.TeacherAssignmentService
import kr.picktime.timetable.assignment.presentation.dto.request.CreateTeacherAssignmentRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schools/{schoolId}/assignments")
class TeacherAssignmentController(
    private val teacherAssignmentService: TeacherAssignmentService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createAssignments(
        @Valid
        @PathVariable schoolId: Long,
        @RequestBody request: CreateTeacherAssignmentRequest
    ) {
        teacherAssignmentService.createAssignments(request)
    }
}