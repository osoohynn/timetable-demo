package kr.picktime.timetable.school.presentation.controller

import kr.picktime.timetable.school.application.service.SchoolService
import kr.picktime.timetable.school.presentation.dto.CreateSchoolRequest
import kr.picktime.timetable.school.presentation.dto.SchoolResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/schools")
class SchoolController(
    private val schoolService: SchoolService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createSchool(@RequestBody request: CreateSchoolRequest): SchoolResponse =
        schoolService.createSchool(request)
}
