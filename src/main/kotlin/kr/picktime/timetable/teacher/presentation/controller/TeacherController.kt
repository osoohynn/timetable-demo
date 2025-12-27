package kr.picktime.timetable.teacher.presentation.controller

import kotlinx.coroutines.flow.toList
import kr.picktime.timetable.teacher.application.service.TeacherService
import kr.picktime.timetable.teacher.domain.entity.TeacherEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/teachers")
class TeacherController(
    private val teacherService: TeacherService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createTeacher(@RequestBody request: CreateTeacherRequest): TeacherEntity =
        teacherService.createTeacher(
            name = request.name,
            schoolId = request.schoolId,
            isActive = request.isActive ?: true
        )

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getTeacherById(@PathVariable id: Long): TeacherEntity =
        teacherService.getTeacherById(id)

    @GetMapping("/school/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllTeachersBySchoolId(@PathVariable schoolId: Long): List<TeacherEntity> =
        teacherService.getAllTeachersBySchoolId(schoolId).toList()

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllTeachers(): List<TeacherEntity> =
        teacherService.getAllTeachers()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateTeacher(
        @PathVariable id: Long,
        @RequestBody request: UpdateTeacherRequest
    ): TeacherEntity =
        teacherService.updateTeacher(
            id = id,
            name = request.name,
            isActive = request.isActive
        )

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteTeacher(@PathVariable id: Long) =
        teacherService.deleteTeacher(id)

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    suspend fun activateTeacher(@PathVariable id: Long): TeacherEntity =
        teacherService.activateTeacher(id)

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    suspend fun deactivateTeacher(@PathVariable id: Long): TeacherEntity =
        teacherService.deactivateTeacher(id)
}

data class CreateTeacherRequest(
    val name: String,
    val schoolId: Long,
    val isActive: Boolean? = true
)

data class UpdateTeacherRequest(
    val name: String? = null,
    val isActive: Boolean? = null
)
