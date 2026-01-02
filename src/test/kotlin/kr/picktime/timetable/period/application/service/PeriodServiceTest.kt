package kr.picktime.timetable.period.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.period.domain.entity.PeriodEntity
import kr.picktime.timetable.period.domain.repository.PeriodRepository
import kr.picktime.timetable.period.exception.PeriodErrorCode
import kr.picktime.timetable.period.presentation.dto.request.CreatePeriodRequest
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.enums.City
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import java.time.DayOfWeek
import java.time.LocalTime

class PeriodServiceTest : DescribeSpec({
    val periodRepository = mockk<PeriodRepository>()
    val schoolRepository = mockk<SchoolRepository>()
    val periodService = PeriodService(periodRepository, schoolRepository)

    describe("createOrUpdatePeriod") {
        context("정상적인 요청이 주어지면") {
            val schoolId = 1L
            val school = SchoolEntity(
                id = schoolId,
                name = "테스트고등학교",
                city = City.SEOUL,
                schoolCode = "TEST001",
                password = "password123",
                isActive = true,
                weeklyClassHours = 2
            )

            val request = CreatePeriodRequest(
                periods = setOf(
                    CreatePeriodRequest.PeriodTimeRequest(
                        dayOfWeek = DayOfWeek.MONDAY,
                        periodNumber = 1,
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(10, 0)
                    ),
                    CreatePeriodRequest.PeriodTimeRequest(
                        dayOfWeek = DayOfWeek.MONDAY,
                        periodNumber = 2,
                        startTime = LocalTime.of(10, 0),
                        endTime = LocalTime.of(11, 0)
                    )
                )
            )

            val savedPeriods = listOf(
                PeriodEntity(
                    id = 1L,
                    schoolId = schoolId,
                    period = 1,
                    startTime = LocalTime.of(9, 0),
                    endTime = LocalTime.of(10, 0),
                    dayOfWeek = DayOfWeek.MONDAY
                ),
                PeriodEntity(
                    id = 2L,
                    schoolId = schoolId,
                    period = 2,
                    startTime = LocalTime.of(10, 0),
                    endTime = LocalTime.of(11, 0),
                    dayOfWeek = DayOfWeek.MONDAY
                )
            )

            coEvery { schoolRepository.findById(schoolId) } returns school
            coEvery { periodRepository.findBySchoolIdAndPeriod(schoolId, any()) } returns null
            coEvery { periodRepository.save(any()) } returnsMany savedPeriods
            coEvery { periodRepository.findAllBySchoolId(schoolId) } returns flowOf(*savedPeriods.toTypedArray())

            it("교시를 생성하고 PeriodResponse 목록을 반환한다") {
                val result = periodService.createOrUpdatePeriod(schoolId, request)

                result.size shouldBe 2
                result[0].period shouldBe 1
                result[0].dayOfWeek shouldBe DayOfWeek.MONDAY
                result[1].period shouldBe 2
                result[1].dayOfWeek shouldBe DayOfWeek.MONDAY

                coVerify(exactly = 1) { schoolRepository.findById(schoolId) }
                coVerify(exactly = 2) { periodRepository.save(any()) }
            }
        }

        context("학교가 존재하지 않으면") {
            val schoolId = 999L
            val request = CreatePeriodRequest(
                periods = setOf(
                    CreatePeriodRequest.PeriodTimeRequest(
                        dayOfWeek = DayOfWeek.MONDAY,
                        periodNumber = 1,
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(10, 0)
                    )
                )
            )

            coEvery { schoolRepository.findById(schoolId) } returns null

            it("SCHOOL_NOT_FOUND 예외를 던진다") {
                val exception = shouldThrow<CustomException> {
                    periodService.createOrUpdatePeriod(schoolId, request)
                }

                exception.errorCode shouldBe SchoolErrorCode.SCHOOL_NOT_FOUND
                coVerify(exactly = 1) { schoolRepository.findById(schoolId) }
                coVerify(exactly = 0) { periodRepository.save(any()) }
            }
        }

        context("주간 시수와 교시 개수가 맞지 않으면") {
            val schoolId = 1L
            val school = SchoolEntity(
                id = schoolId,
                name = "테스트고등학교",
                city = City.SEOUL,
                schoolCode = "TEST001",
                password = "password123",
                isActive = true,
                weeklyClassHours = 35
            )

            val request = CreatePeriodRequest(
                periods = setOf(
                    CreatePeriodRequest.PeriodTimeRequest(
                        dayOfWeek = DayOfWeek.MONDAY,
                        periodNumber = 1,
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(10, 0)
                    )
                )
            )

            coEvery { schoolRepository.findById(schoolId) } returns school

            it("WEEKLY_CLASS_HOURS_MISMATCH 예외를 던진다") {
                val exception = shouldThrow<CustomException> {
                    periodService.createOrUpdatePeriod(schoolId, request)
                }

                exception.errorCode shouldBe PeriodErrorCode.WEEKLY_CLASS_HOURS_MISMATCH
                coVerify(exactly = 1) { schoolRepository.findById(schoolId) }
                coVerify(exactly = 0) { periodRepository.save(any()) }
            }
        }

        context("기존 교시가 있으면") {
            val schoolId = 1L
            val school = SchoolEntity(
                id = schoolId,
                name = "테스트고등학교",
                city = City.SEOUL,
                schoolCode = "TEST001",
                password = "password123",
                isActive = true,
                weeklyClassHours = 1
            )

            val request = CreatePeriodRequest(
                periods = setOf(
                    CreatePeriodRequest.PeriodTimeRequest(
                        dayOfWeek = DayOfWeek.MONDAY,
                        periodNumber = 1,
                        startTime = LocalTime.of(9, 30),
                        endTime = LocalTime.of(10, 30)
                    )
                )
            )

            val existingPeriod = PeriodEntity(
                id = 1L,
                schoolId = schoolId,
                period = 1,
                startTime = LocalTime.of(9, 0),
                endTime = LocalTime.of(10, 0),
                dayOfWeek = DayOfWeek.MONDAY
            )

            val updatedPeriod = existingPeriod.copy(
                startTime = LocalTime.of(9, 30),
                endTime = LocalTime.of(10, 30)
            )

            coEvery { schoolRepository.findById(schoolId) } returns school
            coEvery { periodRepository.findBySchoolIdAndPeriod(schoolId, 1) } returns existingPeriod
            coEvery { periodRepository.save(any()) } returns updatedPeriod
            coEvery { periodRepository.findAllBySchoolId(schoolId) } returns flowOf(updatedPeriod)

            it("기존 교시를 업데이트한다") {
                val result = periodService.createOrUpdatePeriod(schoolId, request)

                result.size shouldBe 1
                result[0].startTime shouldBe LocalTime.of(9, 30)
                result[0].endTime shouldBe LocalTime.of(10, 30)

                coVerify(exactly = 1) { periodRepository.save(any()) }
            }
        }
    }

    describe("getPeriods") {
        context("학교 ID가 주어지면") {
            val schoolId = 1L
            val periods = listOf(
                PeriodEntity(
                    id = 1L,
                    schoolId = schoolId,
                    period = 1,
                    startTime = LocalTime.of(9, 0),
                    endTime = LocalTime.of(10, 0),
                    dayOfWeek = DayOfWeek.MONDAY
                ),
                PeriodEntity(
                    id = 2L,
                    schoolId = schoolId,
                    period = 2,
                    startTime = LocalTime.of(10, 0),
                    endTime = LocalTime.of(11, 0),
                    dayOfWeek = DayOfWeek.MONDAY
                )
            )

            coEvery { periodRepository.findAllBySchoolId(schoolId) } returns flowOf(*periods.toTypedArray())

            it("해당 학교의 모든 교시를 조회한다") {
                val result = periodService.getPeriods(schoolId)

                result.size shouldBe 2
                result[0].period shouldBe 1
                result[1].period shouldBe 2

                coVerify(exactly = 1) { periodRepository.findAllBySchoolId(schoolId) }
            }
        }
    }
})
