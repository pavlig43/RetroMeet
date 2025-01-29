package com.pavlig43.retromeetdata.userinfoRepository.model


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
data class UserInfoUpdateRequest(

    @SerialName("loginId")
    val loginId: Int,

    @SerialName("city")
    val city: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("gender")
    val gender: Gender? = null,

    @SerialName("orientation")
    val orientation: Orientation? = null,

    @SerialName("dateOfBirth")
    val dateOfBirth: Long? = null,

    @SerialName("countChildren")
    val countChildren: Int? = null,

    @SerialName("religion")
    val religion: Religion? = null,

    @SerialName("weight")
    val weight: Int? = null, // Вес пользователя

    @SerialName("height")
    val height: Int? = null, // Рост пользователя

    @SerialName("eyeColor")
    val eyeColor: EyeColor? = null, // Цвет глаз пользователя

    @SerialName("hairColor")
    val hairColor: HairColor? = null, // Цвет волос пользователя

    @SerialName("education")
    val education: Education? = null, // Уровень образования пользователя

    @SerialName("occupation")
    val occupation: String? = null, // Профессия пользователя

    @SerialName("hobbies")
    val hobbies: String? = null, // Хобби пользователя

    @SerialName("interests")
    val interests: String? = null, // Интересы пользователя

    @SerialName("aboutMe")
    val aboutMe: String? = null, // Описание о себе

    @SerialName("smoking")
    val smoking: Smoking? = null, // Курение

    @SerialName("drinking")
    val drinking: Drinking? = null, // Употребление алкоголя

    @SerialName("maritalStatus")
    val maritalStatus: MaritalStatus? = null, // Семейное положение

    @SerialName("pets")
    val pets: List<Pet>? = null, // Домашние животные

    @SerialName("favoriteMusicGenres")
    val favoriteMusicGenres: List<MusicGenre>? = null, // Любимые музыкальные жанры

    @SerialName("favoriteMovies")
    val favoriteMovies: String? = null, // Любимые фильмы

    @SerialName("favoriteBooks")
    val favoriteBooks: String? = null // Любимые книги
)
