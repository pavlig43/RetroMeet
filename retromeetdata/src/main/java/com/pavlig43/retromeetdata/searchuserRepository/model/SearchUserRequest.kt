package com.pavlig43.retromeetdata.searchuserRepository.model

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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUserRequest(
    @SerialName("city")
    val city: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("gender")
    val gender: List<Gender>? = null,
    @SerialName("orientation")
    val orientation: List<Orientation>? = null,
    @SerialName("dateOfBirthRange")
    val dateOfBirthRange: DateOfBirthRange? = null,
    @SerialName("children")
    val children: Boolean? = null,
    @SerialName("religion")
    val religion: List<Religion>? = null,
    @SerialName("weight")
    val weight: WeightRange? = null,
    @SerialName("height")
    val height: HeightRange? = null,
    @SerialName("eyeColor")
    val eyeColor: List<EyeColor>? = null,
    @SerialName("hairColor")
    val hairColor: List<HairColor>? = null,
    @SerialName("education")
    val education: List<Education>? = null,
    @SerialName("smoking")
    val smoking: Smoking? = null,
    @SerialName("drinking")
    val drinking: Drinking? = null,
    @SerialName("maritalStatus")
    val maritalStatus: MaritalStatus? = null,
    @SerialName("pets")
    val pets: List<Pet>? = null,
    @SerialName("favoriteMusicGenres")
    val favoriteMusicGenres: List<MusicGenre>? = null,
)
@Serializable
data class DateOfBirthRange(
    @SerialName("from")
    val from: Long?,
    @SerialName("to")
    val to: Long?
)
@Serializable
data class WeightRange(
    @SerialName("from")
    val from: Int?,
    @SerialName("to")
    val to: Int?
)
@Serializable
data class HeightRange(
    @SerialName("from")
    val from: Int?,
    @SerialName("to")
    val to: Int?
)
