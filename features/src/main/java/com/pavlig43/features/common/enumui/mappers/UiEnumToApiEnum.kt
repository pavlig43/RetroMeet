package com.pavlig43.features.common.enumui.mappers

import com.pavlig43.features.common.enumui.model.DrinkingUi
import com.pavlig43.features.common.enumui.model.EducationUi
import com.pavlig43.features.common.enumui.model.EyeColorUi
import com.pavlig43.features.common.enumui.model.GenderUi
import com.pavlig43.features.common.enumui.model.HairColorUi
import com.pavlig43.features.common.enumui.model.IsHasChildrenUi
import com.pavlig43.features.common.enumui.model.MaritalStatusUi
import com.pavlig43.features.common.enumui.model.MusicGenreUi
import com.pavlig43.features.common.enumui.model.OrientationUi
import com.pavlig43.features.common.enumui.model.PetUi
import com.pavlig43.features.common.enumui.model.ReligionUi
import com.pavlig43.features.common.enumui.model.SmokingUi
import com.pavlig43.retromeetdata.common.Drinking
import com.pavlig43.retromeetdata.common.Education
import com.pavlig43.retromeetdata.common.EyeColor
import com.pavlig43.retromeetdata.common.Gender
import com.pavlig43.retromeetdata.common.HairColor
import com.pavlig43.retromeetdata.common.MaritalStatus
import com.pavlig43.retromeetdata.common.MusicGenre
import com.pavlig43.retromeetdata.common.Orientation
import com.pavlig43.retromeetdata.common.Pet
import com.pavlig43.retromeetdata.common.Religion
import com.pavlig43.retromeetdata.common.Smoking


fun GenderUi.toGender(): Gender? {
    return this.takeIf { it != GenderUi.NULL }
        ?.let { Gender.valueOf(it.name) }
}

fun OrientationUi.toOrientation(): Orientation? {
    return this.takeIf { it != OrientationUi.NULL }
        ?.let { Orientation.valueOf(it.name) }
}

fun ReligionUi.toReligion(): Religion? {
    return this.takeIf { it != ReligionUi.NULL }
        ?.let { Religion.valueOf(it.name) }
}

fun EyeColorUi.toEyeColor(): EyeColor? {
    return this.takeIf { it != EyeColorUi.NULL }
        ?.let { EyeColor.valueOf(it.name) }
}

fun HairColorUi.toHairColor(): HairColor? {
    return this.takeIf { it != HairColorUi.NULL }
        ?.let { HairColor.valueOf(it.name) }
}

fun EducationUi.toEducation(): Education? {
    return this.takeIf { it != EducationUi.NULL }
        ?.let { Education.valueOf(it.name) }
}

fun SmokingUi.toSmoking(): Smoking? {
    return this.takeIf { it != SmokingUi.NULL }
        ?.let { Smoking.valueOf(it.name) }
}

fun DrinkingUi.toDrinking(): Drinking? {
    return this.takeIf { it != DrinkingUi.NULL }
        ?.let { Drinking.valueOf(it.name) }
}

fun MaritalStatusUi.toMaritalStatus(): MaritalStatus? {
    return this.takeIf { it != MaritalStatusUi.NULL }
        ?.let { MaritalStatus.valueOf(it.name) }
}

fun List<PetUi>.toPets(): List<Pet>? {
    return this.takeIf { it.isNotEmpty() }?.let {
        it.map { petUi-> Pet.valueOf(petUi.name) }
    }
}

fun List<MusicGenreUi>.toFavoriteMusicGenres(): List<MusicGenre>? {
    return this.takeIf { it.isNotEmpty() }?.let {
        it.map { genre-> MusicGenre.valueOf(genre.name) }
    }
}
fun IsHasChildrenUi.toBoolean(): Boolean?{
    return this.takeIf { it != IsHasChildrenUi.NULL }?.let {
        it == IsHasChildrenUi.YES
    }
}


