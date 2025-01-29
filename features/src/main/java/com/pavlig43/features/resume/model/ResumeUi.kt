package com.pavlig43.features.resume.model

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

data class ResumeUi(
    val loginId: Int,
    val mainPhotoPath: String? = null,
    val city: String = "",
    val name: String = "",
    val gender: GenderUi = GenderUi.NULL,
    val orientation: OrientationUi = OrientationUi.NULL,
    val dateOfBirth: Long? = null,
    val countChildren: String? = null,
    val religion: ReligionUi = ReligionUi.NULL,
    val weight: String? = null, // Вес пользователя
    val height: String? = null, // Рост пользователя
    val eyeColor: EyeColorUi = EyeColorUi.NULL, // Цвет глаз пользователя
    val hairColor: HairColorUi = HairColorUi.NULL, // Цвет волос пользователя
    val education: EducationUi = EducationUi.NULL, // Уровень образования пользователя
    val occupation: String? = null, // Профессия пользователя
    val hobbies: String? = null, // Хобби пользователя
    val interests: String? = null, // Интересы пользователя
    val aboutMe: String? = null, // Описание о себе
    val smoking: SmokingUi = SmokingUi.NULL, // Курение
    val drinking: DrinkingUi = DrinkingUi.NULL, // Употребление алкоголя
    val maritalStatus: MaritalStatusUi = MaritalStatusUi.NULL, // Семейное положение
    val pets: List<PetUi> = listOf(), // Домашние животные
    val favoriteMusicGenres: List<MusicGenreUi> = listOf(), // Любимые музыкальные жанры
    val favoriteMovies: String? = null, // Любимые фильмы
    val favoriteBooks: String? = null // Любимые книги
)
