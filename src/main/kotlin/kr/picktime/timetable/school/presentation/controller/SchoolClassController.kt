package kr.picktime.timetable.school.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.school.application.service.SchoolClassService
import kr.picktime.timetable.school.presentation.dto.request.CreateSchoolClassRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}