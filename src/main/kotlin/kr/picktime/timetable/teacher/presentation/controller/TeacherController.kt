package kr.picktime.timetable.teacher.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.teacher.application.service.TeacherService
import kr.picktime.timetable.teacher.presentation.dto.request.CreateTeacherRequest
import kr.picktime.timetable.teacher.presentation.dto.response.TeacherResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schools/{schoolId}/teachers")
class TeacherController(
    private val teacherService: TeacherService
) {
    @PostMapping
    suspend fun createTeacher(
        @Valid
        @PathVariable schoolId: Long,
        @RequestBody request: CreateTeacherRequest
    ): TeacherResponse {
        return teacherService.createTeacher(schoolId, request)
    }

    @GetMapping
    suspend fun getTeachers(@PathVariable schoolId: Long): List<TeacherResponse> {
        return teacherService.getTeachers(schoolId)
    }
}
