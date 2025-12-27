package kr.picktime.timetable.school.presentation.controller

import kr.picktime.timetable.school.application.service.SchoolService
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.enums.City
import kr.picktime.timetable.school.presentation.controller.dto.request.CreateSchoolRequest
import kr.picktime.timetable.school.presentation.controller.dto.request.UpdateSchoolRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/schools")
class SchoolController(
    private val schoolService: SchoolService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createSchool(@RequestBody request: CreateSchoolRequest): SchoolEntity =
        schoolService.createSchool(
            name = request.name,
            city = request.city,
            schoolCode = request.schoolCode,
            password = request.password,
            isActive = request.isActive ?: true
        )

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getSchoolById(@PathVariable id: Long): SchoolEntity =
        schoolService.getSchoolById(id)

    @GetMapping("/code/{schoolCode}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getSchoolBySchoolCode(@PathVariable schoolCode: String): SchoolEntity =
        schoolService.getSchoolBySchoolCode(schoolCode)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllSchools(): List<SchoolEntity> =
        schoolService.getAllSchools()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateSchool(
        @PathVariable id: Long,
        @RequestBody request: UpdateSchoolRequest
    ): SchoolEntity =
        schoolService.updateSchool(
            id = id,
            name = request.name,
            city = request.city,
            password = request.password,
            isActive = request.isActive
        )

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteSchool(@PathVariable id: Long) =
        schoolService.deleteSchool(id)

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    suspend fun activateSchool(@PathVariable id: Long): SchoolEntity =
        schoolService.activateSchool(id)

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    suspend fun deactivateSchool(@PathVariable id: Long): SchoolEntity =
        schoolService.deactivateSchool(id)
}
