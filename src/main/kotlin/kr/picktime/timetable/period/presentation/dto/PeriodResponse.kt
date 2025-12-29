package kr.picktime.timetable.period.presentation.dto

import kr.picktime.timetable.period.domain.entity.PeriodSettingEntity
import kr.picktime.timetable.period.domain.entity.PeriodTimeEntity

data class PeriodResponse(
    val periodSettings: List<PeriodSettingEntity>,
    val periodTimes: List<PeriodTimeEntity>
)

data class DayPeriodResponse(
    val periodSetting: PeriodSettingEntity,
    val periodTimes: List<PeriodTimeEntity>
)