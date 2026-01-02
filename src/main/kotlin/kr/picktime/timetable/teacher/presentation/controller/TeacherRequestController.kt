package kr.picktime.timetable.teacher.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.teacher.application.service.TeacherRequestService
import kr.picktime.timetable.teacher.presentation.dto.request.CreateTeacherRequestRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schools/{schoolId}/teacher/{teacherId}/requests")
class TeacherRequestController(
    private val teacherRequestService: TeacherRequestService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createRequests(
        @Valid
        @PathVariable schoolId: Long,
        @PathVariable teacherId: Long,
        @RequestBody request: CreateTeacherRequestRequest
    ) {
        teacherRequestService.createRequests(teacherId, request)
    }
}