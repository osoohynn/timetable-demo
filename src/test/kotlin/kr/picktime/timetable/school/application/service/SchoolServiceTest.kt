package kr.picktime.timetable.school.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.enums.City
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.school.presentation.dto.CreateSchoolRequest

class SchoolServiceTest : DescribeSpec({
    val schoolRepository = mockk<SchoolRepository>()
    val schoolService = SchoolService(schoolRepository)

    describe("createSchool") {
        context("정상적인 요청이 주어지면") {
            val request = CreateSchoolRequest(
                name = "테스트고등학교",
                city = City.SEOUL,
                schoolCode = "TEST001",
                password = "password123",
                isActive = true,
                weeklyClassHours = 35
            )

            val savedSchool = SchoolEntity(
                id = 1L,
                name = request.name,
                city = request.city,
                schoolCode = request.schoolCode,
                password = request.password,
                isActive = true,
                weeklyClassHours = request.weeklyClassHours
            )

            coEvery { schoolRepository.existsBySchoolCode(request.schoolCode) } returns false
            coEvery { schoolRepository.save(any()) } returns savedSchool

            it("학교를 생성하고 SchoolResponse를 반환한다") {
                val result = schoolService.createSchool(request)

                result.id shouldBe 1L
                result.name shouldBe "테스트고등학교"
                result.city shouldBe City.SEOUL
                result.schoolCode shouldBe "TEST001"
                result.weeklyClassHours shouldBe 35L
                result.isActive shouldBe true

                coVerify(exactly = 1) { schoolRepository.existsBySchoolCode(request.schoolCode) }
                coVerify(exactly = 1) { schoolRepository.save(any()) }
            }
        }

        context("중복된 학교 코드가 존재하면") {
            val request = CreateSchoolRequest(
                name = "테스트고등학교",
                city = City.SEOUL,
                schoolCode = "TEST001",
                password = "password123",
                isActive = true,
                weeklyClassHours = 35
            )

            coEvery { schoolRepository.existsBySchoolCode(request.schoolCode) } returns true

            it("SCHOOL_CODE_ALREADY_EXISTS 예외를 던진다") {
                val exception = shouldThrow<CustomException> {
                    schoolService.createSchool(request)
                }

                exception.errorCode shouldBe SchoolErrorCode.SCHOOL_CODE_ALREADY_EXISTS
                coVerify(exactly = 1) { schoolRepository.existsBySchoolCode(request.schoolCode) }
                coVerify(exactly = 0) { schoolRepository.save(any()) }
            }
        }

        context("isActive가 null이면") {
            val request = CreateSchoolRequest(
                name = "테스트고등학교",
                city = City.SEOUL,
                schoolCode = "TEST001",
                password = "password123",
                isActive = null,
                weeklyClassHours = 35
            )

            val savedSchool = SchoolEntity(
                id = 1L,
                name = request.name,
                city = request.city,
                schoolCode = request.schoolCode,
                password = request.password,
                isActive = true,
                weeklyClassHours = request.weeklyClassHours
            )

            coEvery { schoolRepository.existsBySchoolCode(request.schoolCode) } returns false
            coEvery { schoolRepository.save(any()) } returns savedSchool

            it("기본값 true로 설정된다") {
                val result = schoolService.createSchool(request)

                result.isActive shouldBe true
            }
        }
    }
})
