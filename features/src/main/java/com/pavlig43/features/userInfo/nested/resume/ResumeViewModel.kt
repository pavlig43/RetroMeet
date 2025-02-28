package com.pavlig43.features.userInfo.nested.resume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlig43.features.common.enumui.model.DrinkingUi
import com.pavlig43.features.common.enumui.model.EducationUi
import com.pavlig43.features.common.enumui.model.EnumUi
import com.pavlig43.features.common.enumui.model.EyeColorUi
import com.pavlig43.features.common.enumui.model.GenderUi
import com.pavlig43.features.common.enumui.model.HairColorUi
import com.pavlig43.features.common.enumui.model.MaritalStatusUi
import com.pavlig43.features.common.enumui.model.MusicGenreUi
import com.pavlig43.features.common.enumui.model.OrientationUi
import com.pavlig43.features.common.enumui.model.PetUi
import com.pavlig43.features.common.enumui.model.ReligionUi
import com.pavlig43.features.common.enumui.model.SmokingUi
import com.pavlig43.features.common.enumui.mappers.toDrinking
import com.pavlig43.features.common.enumui.mappers.toDrinkingUI
import com.pavlig43.features.common.enumui.mappers.toEducation
import com.pavlig43.features.common.enumui.mappers.toEducationUI
import com.pavlig43.features.common.enumui.mappers.toEyeColor
import com.pavlig43.features.common.enumui.mappers.toEyeColorUI
import com.pavlig43.features.common.enumui.mappers.toFavoriteMusicGenres
import com.pavlig43.features.common.enumui.mappers.toFavoriteMusicGenresUI
import com.pavlig43.features.common.enumui.mappers.toGender
import com.pavlig43.features.common.enumui.mappers.toGenderUI
import com.pavlig43.features.common.enumui.mappers.toHairColor
import com.pavlig43.features.common.enumui.mappers.toHairColorUI
import com.pavlig43.features.common.enumui.mappers.toMaritalStatus
import com.pavlig43.features.common.enumui.mappers.toMaritalStatusUI
import com.pavlig43.features.common.enumui.mappers.toOrientation
import com.pavlig43.features.common.enumui.mappers.toOrientationUi
import com.pavlig43.features.common.enumui.mappers.toPets
import com.pavlig43.features.common.enumui.mappers.toPetsUI
import com.pavlig43.features.common.enumui.mappers.toReligion
import com.pavlig43.features.common.enumui.mappers.toReligionUI
import com.pavlig43.features.common.enumui.mappers.toSmoking
import com.pavlig43.features.common.enumui.mappers.toSmokingUI
import com.pavlig43.features.userInfo.nested.resume.model.ResumeUi
import com.pavlig43.features.utils.convertToDate
import com.pavlig43.retromeetdata.resumeRepository.ResumeRepository
import com.pavlig43.retromeetdata.resumeRepository.model.ResumeResponse
import com.pavlig43.retromeetdata.resumeRepository.model.ResumeUpdateRequest
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.mapTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository
) : ViewModel() {


    private val _resumeState = MutableStateFlow<ResumeState>(ResumeState.Loading)
    val resumeState = _resumeState.asStateFlow()

    private val _resumeUi = MutableStateFlow(ResumeUi(0,))
    val resumeUi = _resumeUi.asStateFlow()


    init {
        viewModelScope.launch {
            getResume()
        }
    }


    private suspend fun getResume() {
        val start = resumeRepository.getResume().mapTo { it.toResumeUi() }.toResumeState()

        _resumeState.update { start }
        if (_resumeState.value is ResumeState.Success) {
            _resumeUi.update { (_resumeState.value as ResumeState.Success).data }
        }
    }

    fun changeName(newName: String) {
        _resumeUi.update { it.copy(name = newName) }
    }

    fun setDate(date: Long?) {
        _resumeUi.update { it.copy(dateOfBirth = date) }
    }

    fun changeCountChildren(countChildren: String) {
        _resumeUi.update { it.copy(countChildren = countChildren) }
    }

    fun changeWeight(newWeight: String) {
        _resumeUi.update { it.copy(weight = newWeight) }
    }

    fun changeHeight(newHeight: String) {
        _resumeUi.update { it.copy(height = newHeight) }
    }

    fun onChoiceSingleEnumUi(enum: EnumUi) {
        when (enum) {
            is GenderUi -> _resumeUi.update { it.copy(gender = enum) }
            is OrientationUi -> _resumeUi.update { it.copy(orientation = enum) }
            is ReligionUi -> _resumeUi.update { it.copy(religion = enum) }
            is EyeColorUi -> _resumeUi.update { it.copy(eyeColor = enum) }
            is HairColorUi -> _resumeUi.update { it.copy(hairColor = enum) }
            is EducationUi -> _resumeUi.update { it.copy(education = enum) }
            is DrinkingUi -> _resumeUi.update { it.copy(drinking = enum) }
            is SmokingUi -> _resumeUi.update { it.copy(smoking = enum) }
            is MaritalStatusUi -> _resumeUi.update { it.copy(maritalStatus = enum) }
        }
    }

    fun addEnumUi(enum: EnumUi) {
        when (enum) {
            is PetUi -> _resumeUi.update { it.copy(pets = it.pets + enum) }
            is MusicGenreUi -> _resumeUi.update { it.copy(favoriteMusicGenres = it.favoriteMusicGenres + enum) }
        }
    }

    fun removeEnumUi(enum: EnumUi) {
        when (enum) {
            is PetUi -> _resumeUi.update { it.copy(pets = it.pets - enum) }
            is MusicGenreUi -> _resumeUi.update { it.copy(favoriteMusicGenres = it.favoriteMusicGenres - enum) }
        }
    }

    fun updateResume(returnToMainScreen: () -> Unit) {
        viewModelScope.launch {
            resumeRepository.updateResume(_resumeUi.value.toUserInfoUpdateRequest())
            returnToMainScreen()
        }
    }


}

sealed class ResumeState {
    data object Loading : ResumeState()
    data class Success(val data: ResumeUi) : ResumeState()
    data class Error(val errorMessage: String) : ResumeState()
}

fun RequestResult<ResumeUi>.toResumeState(): ResumeState {
    return when (val result = this) {
        is RequestResult.Error -> ResumeState.Error(result.throwable?.message ?: "Unknown error")
        is RequestResult.InProgress -> ResumeState.Loading
        is RequestResult.Success -> result.data?.let { ResumeState.Success(it) }
            ?: ResumeState.Error("Unknown error")
    }
}

internal fun ResumeResponse.toResumeUi(): ResumeUi {
    return ResumeUi(
        userId = userId,
        registeredAt = registeredAt.convertToDate()!!,
        city = city ?: "",
        mainPhotoPath = mainPhotoPath,
        name = name ?: "",
        gender = gender.toGenderUI(),
        orientation = orientation.toOrientationUi(),
        dateOfBirth = dateOfBirth,
        countChildren = countChildren?.toString(),
        religion = religion.toReligionUI(),
        weight = weight?.toString(), // Вес пользователя
        height = height?.toString(), // Рост пользователя
        eyeColor = eyeColor.toEyeColorUI(), // Цвет глаз пользователя
        hairColor = hairColor.toHairColorUI(), // Цвет волос пользователя
        education = education.toEducationUI(), // Уровень образования пользователя
        occupation = occupation, // Профессия пользователя
        hobbies = hobbies, // Хобби пользователя
        interests = interests, // Интересы пользователя
        aboutMe = aboutMe, // Описание о себе
        smoking = smoking.toSmokingUI(),
        drinking = drinking.toDrinkingUI(),
        maritalStatus = maritalStatus.toMaritalStatusUI(), // Семейное положение
        pets = pets.toPetsUI(),
        favoriteMusicGenres = favoriteMusicGenres.toFavoriteMusicGenresUI(),
        favoriteMovies = favoriteMovies, // Любимые фильмы
        favoriteBooks = favoriteBooks // Любимые книги
    )
}
private fun ResumeUi.toUserInfoUpdateRequest(): ResumeUpdateRequest {
    return ResumeUpdateRequest(
        userId = userId,
        name = name.takeIf { it.isNotBlank() },
        city = city,
        gender = gender.toGender(),
        orientation = orientation.toOrientation(),
        dateOfBirth = dateOfBirth,
        countChildren = countChildren?.toIntOrNull(),
        religion = religion.toReligion(),
        weight = weight?.toIntOrNull(),
        height = height?.toIntOrNull(),
        eyeColor = eyeColor.toEyeColor(),
        hairColor = hairColor.toHairColor(),
        education = education.toEducation(),
        occupation = occupation,
        hobbies = hobbies,
        interests = interests,
        aboutMe = aboutMe,
        smoking = smoking.toSmoking(),
        drinking = drinking.toDrinking(),
        maritalStatus = maritalStatus.toMaritalStatus(),
        pets = pets.toPets(),
        favoriteMusicGenres = favoriteMusicGenres.toFavoriteMusicGenres(),
        favoriteMovies = favoriteMovies,
        favoriteBooks = favoriteBooks
    )
}


