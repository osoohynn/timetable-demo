package kr.picktime.timetable.school.presentation.controller

import kotlinx.coroutines.flow.toList
import kr.picktime.timetable.school.application.service.SchoolClassService
import kr.picktime.timetable.school.domain.entity.SchoolClassEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/school-classes")
class SchoolClassController(
    private val schoolClassService: SchoolClassService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createSchoolClass(@RequestBody request: CreateSchoolClassRequest): SchoolClassEntity =
        schoolClassService.createSchoolClass(
            schoolId = request.schoolId,
            grade = request.grade,
            classNumber = request.classNumber,
            isActive = request.isActive ?: true
        )

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getSchoolClassById(@PathVariable id: Long): SchoolClassEntity =
        schoolClassService.getSchoolClassById(id)

    @GetMapping("/school/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllSchoolClassesBySchoolId(@PathVariable schoolId: Long): List<SchoolClassEntity> =
        schoolClassService.getAllSchoolClassesBySchoolId(schoolId).toList()

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllSchoolClasses(): List<SchoolClassEntity> =
        schoolClassService.getAllSchoolClasses()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateSchoolClass(
        @PathVariable id: Long,
        @RequestBody request: UpdateSchoolClassRequest
    ): SchoolClassEntity =
        schoolClassService.updateSchoolClass(
            id = id,
            grade = request.grade,
            classNumber = request.classNumber,
            isActive = request.isActive
        )

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteSchoolClass(@PathVariable id: Long) =
        schoolClassService.deleteSchoolClass(id)

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    suspend fun activateSchoolClass(@PathVariable id: Long): SchoolClassEntity =
        schoolClassService.activateSchoolClass(id)

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    suspend fun deactivateSchoolClass(@PathVariable id: Long): SchoolClassEntity =
        schoolClassService.deactivateSchoolClass(id)
}

data class CreateSchoolClassRequest(
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int,
    val isActive: Boolean? = true
)

data class UpdateSchoolClassRequest(
    val grade: Int? = null,
    val classNumber: Int? = null,
    val isActive: Boolean? = null
)
