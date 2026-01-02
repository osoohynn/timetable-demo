package kr.picktime.timetable.subject.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kr.picktime.timetable.global.exception.CustomException
import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.enums.City
import kr.picktime.timetable.school.domain.repository.SchoolRepository
import kr.picktime.timetable.school.exception.SchoolErrorCode
import kr.picktime.timetable.subject.domain.entity.SubjectEntity
import kr.picktime.timetable.subject.domain.entity.SubjectSpecialRoomEntity
import kr.picktime.timetable.subject.domain.repository.SubjectRepository
import kr.picktime.timetable.subject.domain.repository.SubjectSpecialRoomRepository
import kr.picktime.timetable.subject.presentation.dto.request.CreateSubjectRequest

class SubjectServiceTest : DescribeSpec({
    describe("createSubject") {
        context("정상적인 요청이 주어지면") {
            val subjectRepository = mockk<SubjectRepository>()
            val schoolRepository = mockk<SchoolRepository>()
            val subjectSpecialRoomRepository = mockk<SubjectSpecialRoomRepository>()
            val subjectService = SubjectService(subjectRepository, schoolRepository, subjectSpecialRoomRepository)
            val schoolId = 1L
            val request = CreateSubjectRequest(
                name = "수학",
                shortName = "수학",
                specialRoomIds = listOf(1L, 2L)
            )

            val school = SchoolEntity(
                id = schoolId,
                name = "테스트고등학교",
                city = City.SEOUL,
                schoolCode = "TEST001",
                password = "password123",
                isActive = true,
                weeklyClassHours = 35
            )

            val savedSubject = SubjectEntity(
                id = 1L,
                schoolId = schoolId,
                name = request.name,
                shortName = request.shortName
            )

            coEvery { schoolRepository.findById(schoolId) } returns school
            coEvery { subjectRepository.save(any()) } returns savedSubject
            coEvery { subjectSpecialRoomRepository.save(any()) } returns SubjectSpecialRoomEntity(
                id = 1L,
                subjectId = 1L,
                specialRoomId = 1L
            )

            it("과목을 생성하고 SubjectResponse를 반환한다") {
                val result = subjectService.createSubject(schoolId, request)

                result.id shouldBe 1L
                result.schoolId shouldBe schoolId
                result.name shouldBe "수학"
                result.shortName shouldBe "수학"

                coVerify(exactly = 1) { schoolRepository.findById(schoolId) }
                coVerify(exactly = 1) { subjectRepository.save(any()) }
                coVerify(exactly = 2) { subjectSpecialRoomRepository.save(any()) }
            }
        }

        context("존재하지 않는 학교 ID가 주어지면") {
            val subjectRepository = mockk<SubjectRepository>()
            val schoolRepository = mockk<SchoolRepository>()
            val subjectSpecialRoomRepository = mockk<SubjectSpecialRoomRepository>()
            val subjectService = SubjectService(subjectRepository, schoolRepository, subjectSpecialRoomRepository)

            val schoolId = 999L
            val request = CreateSubjectRequest(
                name = "수학",
                shortName = "수학",
                specialRoomIds = emptyList()
            )

            coEvery { schoolRepository.findById(schoolId) } returns null

            it("SCHOOL_NOT_FOUND 예외를 던진다") {
                val exception = shouldThrow<CustomException> {
                    subjectService.createSubject(schoolId, request)
                }

                exception.errorCode shouldBe SchoolErrorCode.SCHOOL_NOT_FOUND
                coVerify(exactly = 1) { schoolRepository.findById(schoolId) }
                coVerify(exactly = 0) { subjectRepository.save(any()) }
            }
        }

        context("짧은 이름과 긴 이름이 다른 과목을 생성하면") {
            val subjectRepository = mockk<SubjectRepository>()
            val schoolRepository = mockk<SchoolRepository>()
            val subjectSpecialRoomRepository = mockk<SubjectSpecialRoomRepository>()
            val subjectService = SubjectService(subjectRepository, schoolRepository, subjectSpecialRoomRepository)

            val schoolId = 1L
            val request = CreateSubjectRequest(
                name = "영어회화",
                shortName = "영회",
                specialRoomIds = listOf(3L)
            )

            val school = SchoolEntity(
                id = schoolId,
                name = "테스트고등학교",
                city = City.SEOUL,
                schoolCode = "TEST001",
                password = "password123",
                isActive = true,
                weeklyClassHours = 35
            )

            val savedSubject = SubjectEntity(
                id = 2L,
                schoolId = schoolId,
                name = request.name,
                shortName = request.shortName
            )

            coEvery { schoolRepository.findById(schoolId) } returns school
            coEvery { subjectRepository.save(any()) } returns savedSubject
            coEvery { subjectSpecialRoomRepository.save(any()) } returns SubjectSpecialRoomEntity(
                id = 1L,
                subjectId = 2L,
                specialRoomId = 3L
            )

            it("요청한 이름과 짧은 이름으로 과목을 생성한다") {
                val result = subjectService.createSubject(schoolId, request)

                result.name shouldBe "영어회화"
                result.shortName shouldBe "영회"
                coVerify(exactly = 1) { subjectSpecialRoomRepository.save(any()) }
            }
        }
    }
})
