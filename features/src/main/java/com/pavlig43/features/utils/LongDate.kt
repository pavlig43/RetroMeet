package com.pavlig43.features.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import java.util.Locale


fun Long?.getCountYearsToCurrentYear(): Int? {
    return this?.let {
        val date = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
        val currentYear = LocalDate.now().year
        currentYear - date.year
    }
}

fun Long?.convertToDate(): String? {
    return this?.let {
        val formatter = getLocalizedDateTimeFormatter(Locale.getDefault())
        val formattedDate =
            formatter.format(Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate())
        val parts = formattedDate.split(Regex("[/\\-.,]"))
        parts.joinToString(".") { part -> part.padStart(2, '0') }
    }

}



private fun getLocalizedDateTimeFormatter(locale: Locale): DateTimeFormatter {
    var pattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
        FormatStyle.SHORT,
        null,
        IsoChronology.INSTANCE,
        locale
    )
    pattern = pattern.replace("yy", "yyyy")
    return DateTimeFormatter.ofPattern(pattern)
}

/**
 * Получаем дату рождения в Unix исходя из количества лет
 */
fun String.toDateOfBirthUnix(): Long?{
    return this.toLongOrNull()?.let {
        val dateOfBirth = LocalDate.now().minusYears(it)
        dateOfBirth.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

}



