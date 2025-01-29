package com.pavlig43.features.common.enumui.mappers

import com.pavlig43.features.common.enumui.model.DrinkingUi
import com.pavlig43.features.common.enumui.model.EducationUi
import com.pavlig43.features.common.enumui.model.EyeColorUi
import com.pavlig43.features.common.enumui.model.GenderUi
import com.pavlig43.features.common.enumui.model.HairColorUi
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


fun Gender?.toGenderUI(): GenderUi {
    return this?.let {
        GenderUi.valueOf(it.name)
    } ?: GenderUi.NULL
}

fun Orientation?.toOrientationUi(): OrientationUi {
    return this?.let {
        OrientationUi.valueOf(it.name)
    } ?: OrientationUi.NULL
}

fun Religion?.toReligionUI(): ReligionUi {
    return this?.let {
        ReligionUi.valueOf(it.name)
    } ?: ReligionUi.NULL
}

fun EyeColor?.toEyeColorUI(): EyeColorUi {
    return this?.let {
        EyeColorUi.valueOf(it.name)
    } ?: EyeColorUi.NULL
}

fun HairColor?.toHairColorUI(): HairColorUi {
    return this?.let {
        HairColorUi.valueOf(it.name)
    } ?: HairColorUi.NULL
}

fun Education?.toEducationUI(): EducationUi {
    return this?.let {
        EducationUi.valueOf(it.name)
    } ?: EducationUi.NULL
}

fun Smoking?.toSmokingUI(): SmokingUi {
    return this?.let {
        SmokingUi.valueOf(it.name)
    } ?: SmokingUi.NULL
}

fun Drinking?.toDrinkingUI(): DrinkingUi {
    return this?.let {
        DrinkingUi.valueOf(it.name)
    } ?: DrinkingUi.NULL
}

fun MaritalStatus?.toMaritalStatusUI(): MaritalStatusUi {
    return this?.let {
        MaritalStatusUi.valueOf(it.name)
    } ?: MaritalStatusUi.NULL
}

fun List<Pet>?.toPetsUI(): List<PetUi> {
    return this?.map { PetUi.valueOf(it.name) } ?: listOf()
}

fun List<MusicGenre>?.toFavoriteMusicGenresUI(): List<MusicGenreUi> {
    return this?.map { MusicGenreUi.valueOf(it.name) } ?: listOf()
}


