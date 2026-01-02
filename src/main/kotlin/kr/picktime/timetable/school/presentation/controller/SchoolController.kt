package kr.picktime.timetable.school.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.school.application.service.SchoolService
import kr.picktime.timetable.school.presentation.dto.request.CreateSchoolRequest
import kr.picktime.timetable.school.presentation.dto.response.SchoolResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schools")
class SchoolController(
    private val schoolService: SchoolService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createSchool(@Valid @RequestBody request: CreateSchoolRequest): SchoolResponse =
        schoolService.createSchool(request)
}
