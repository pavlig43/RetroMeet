package com.pavlig43.features.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.yearsUntil
import java.time.ZoneId

private fun nowDateInStartDay(): Instant {
    return Clock.System.now().toLocalDateTime(TimeZone.UTC).date.atStartOfDayIn(TimeZone.UTC)
}


fun Long?.getCountYearsToCurrentYear(): Int? {
    return this?.let {
        val start = nowDateInStartDay()
        val end =
            Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.UTC).date.atStartOfDayIn(
                TimeZone.UTC
            )
        val years = end.yearsUntil(start, TimeZone.UTC)
        years
    }
}

private fun LocalDateTime.toRussianFormat(withTime: Boolean = false): String {
    val format = LocalDateTime.Format {
        dayOfMonth()
        char('.')
        monthNumber()
        char('.')
        year()
        if (withTime) {
            char(' ')
            hour()
            char(':')
            minute()
        }

    }

    return format.format(this)
}



fun Long?.convertToDate(): String? {
    return this?.let {

        Instant.fromEpochMilliseconds(it)
            .toLocalDateTime(TimeZone.currentSystemDefault()).toRussianFormat()
    }

}


fun String.maxDateOfBirthUnix(): Long? {
    return this.toLongOrNull()?.let { age ->
        val minDateOfBirthTimeStamp =
            nowDateInStartDay().minus(age + 1, DateTimeUnit.YEAR, TimeZone.UTC)
                .plus(1, DateTimeUnit.DAY, TimeZone.UTC).toEpochMilliseconds()
        minDateOfBirthTimeStamp
    }
}

fun String.minDateOfBirthUnix(): Long? {
    return this.toLongOrNull()?.let { age ->
        val maxDateOfBirthTimeStamp =
            nowDateInStartDay().minus(age, DateTimeUnit.YEAR, TimeZone.UTC).toEpochMilliseconds()
        maxDateOfBirthTimeStamp
    }
}


/**
 * 12.02.2025, 11:17
 */
fun Long.convertToDateTime(): String {
    return this.let {
        Instant.fromEpochMilliseconds(it)
            .toLocalDateTime(TimeZone.currentSystemDefault()).toRussianFormat(true)
    }
}

fun main() {


    val start = 415065600000.getCountYearsToCurrentYear()
    val end = 446688000000.getCountYearsToCurrentYear()
    println(start)
    println(end)

    val age = "30"
    val min = age.minDateOfBirthUnix().convertToDate()
    val max = age.maxDateOfBirthUnix().convertToDate()



}



