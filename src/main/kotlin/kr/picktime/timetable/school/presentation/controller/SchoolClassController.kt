package kr.picktime.timetable.school.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.school.application.service.SchoolClassService
import kr.picktime.timetable.school.presentation.dto.request.CreateSchoolClassRequest
import kr.picktime.timetable.school.presentation.dto.response.SchoolClassResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schools/{schoolId}/classes")
class SchoolClassController(
    private val schoolClassService: SchoolClassService
) {
    @PostMapping
    suspend fun createSchoolClass(
        @Valid
        @PathVariable schoolId: Long,
        @RequestBody request: CreateSchoolClassRequest
    ) {
        schoolClassService.createClass(schoolId, request)
    }

    @GetMapping
    suspend fun getClasses(@PathVariable schoolId: Long): List<SchoolClassResponse> {
        return schoolClassService.getClasses(schoolId)
    }
}