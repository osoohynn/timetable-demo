package kr.picktime.timetable.specialroom.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.specialroom.application.service.SpecialRoomService
import kr.picktime.timetable.specialroom.presentation.dto.request.CreateSpecialRoomRequest
import kr.picktime.timetable.specialroom.presentation.dto.response.SpecialRoomResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schools/{schoolId}/special-rooms")
class SpecialRoomController(
    private val specialRoomService: SpecialRoomService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createSpecialRoom(
        @Valid
        @PathVariable schoolId: Long,
        @RequestBody request: CreateSpecialRoomRequest
    ): SpecialRoomResponse =
        specialRoomService.createSpecialRoom(schoolId, request)

    @GetMapping
    suspend fun getSpecialRooms(@PathVariable schoolId: Long): List<SpecialRoomResponse> =
        specialRoomService.getSpecialRooms(schoolId)
}
