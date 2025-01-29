package com.pavlig43.features.searchuser.model

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
import kotlinx.serialization.Serializable


@Serializable
data class SearchUserRequestUi(
    val city: String = "",
    val name: String = "",
    val gender: List<GenderUi> = listOf(),
    val orientation: List<OrientationUi> = listOf(),
    val dateOfBirthRange: DateOfBirthRangeUi = DateOfBirthRangeUi(),
    val children: IsHasChildrenUi = IsHasChildrenUi.NULL,
    val religion: List<ReligionUi> = listOf(),
    val weight: WeightRangeUi = WeightRangeUi(),
    val height: HeightRangeUi = HeightRangeUi(),
    val eyeColor: List<EyeColorUi> = listOf(),
    val hairColor: List<HairColorUi> = listOf(),
    val education: List<EducationUi> = listOf(),
    val smoking: SmokingUi = SmokingUi.NULL,
    val drinking: DrinkingUi = DrinkingUi.NULL,
    val maritalStatus: MaritalStatusUi = MaritalStatusUi.NULL,
    val pets: List<PetUi> = listOf(),
    val favoriteMusicGenres: List<MusicGenreUi> = listOf(),
)

@Serializable
data class DateOfBirthRangeUi(
    val from: String = "",
    val to: String = ""
)

@Serializable
data class WeightRangeUi(
    val from: String = "",
    val to: String = ""
)

@Serializable
data class HeightRangeUi(
    val from: String = "",
    val to: String = ""
)
