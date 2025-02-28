package com.pavlig43.features.searchuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlig43.features.common.enumui.mappers.toBoolean
import com.pavlig43.features.common.enumui.mappers.toDrinking
import com.pavlig43.features.common.enumui.mappers.toEducation
import com.pavlig43.features.common.enumui.mappers.toEyeColor
import com.pavlig43.features.common.enumui.mappers.toFavoriteMusicGenres
import com.pavlig43.features.common.enumui.mappers.toGender
import com.pavlig43.features.common.enumui.mappers.toHairColor
import com.pavlig43.features.common.enumui.mappers.toMaritalStatus
import com.pavlig43.features.common.enumui.mappers.toOrientation
import com.pavlig43.features.common.enumui.mappers.toPets
import com.pavlig43.features.common.enumui.mappers.toReligion
import com.pavlig43.features.common.enumui.mappers.toSmoking
import com.pavlig43.features.common.enumui.model.DrinkingUi
import com.pavlig43.features.common.enumui.model.EducationUi
import com.pavlig43.features.common.enumui.model.EnumUi
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
import com.pavlig43.features.searchuser.model.DateOfBirthRangeUi
import com.pavlig43.features.searchuser.model.HeightRangeUi
import com.pavlig43.features.searchuser.model.SearchUserRequestUi
import com.pavlig43.features.searchuser.model.WeightRangeUi
import com.pavlig43.features.utils.maxDateOfBirthUnix
import com.pavlig43.features.utils.minDateOfBirthUnix
import com.pavlig43.retromeetdata.searchuserRepository.SearchUserRepository
import com.pavlig43.retromeetdata.searchuserRepository.model.DateOfBirthRange
import com.pavlig43.retromeetdata.searchuserRepository.model.HeightRange
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserFilterRequest
import com.pavlig43.retromeetdata.searchuserRepository.model.WeightRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class SearchUsersViewModel @Inject constructor(
    private val searchUserRepository: SearchUserRepository
) : ViewModel() {


    private val _searchRequest = MutableStateFlow(SearchUserRequestUi(isOnline = false))
    val searchRequest = _searchRequest.asStateFlow()

    fun onChangeOnline(value:Boolean){
        _searchRequest.update { it.copy(isOnline = value) }
    }


    fun changeName(newName: String) {
        _searchRequest.update { it.copy(name = newName) }
    }

    fun changeCity(city: String) {
        _searchRequest.update { it.copy(city = city) }
    }

    fun onChangeAgeFrom(age: String) {
        _searchRequest.update {
            it.copy(
                dateOfBirthRange = _searchRequest.value.dateOfBirthRange.copy(
                    from = age
                )
            )
        }
    }

    fun onChangeAgeTo(age: String) {
        _searchRequest.update {
            it.copy(
                dateOfBirthRange = _searchRequest.value.dateOfBirthRange.copy(
                    to = age
                )
            )
        }
    }

    fun onChangeWeightFrom(weight: String) {
        _searchRequest.update {
            it.copy(
                weight = _searchRequest.value.weight.copy(
                    from = weight
                )
            )
        }
    }

    fun onChangeWeightTo(weight: String) {
        _searchRequest.update {
            it.copy(
                weight = _searchRequest.value.weight.copy(
                    to = weight
                )
            )
        }
    }

    fun onChangeHeightFrom(height: String) {
        _searchRequest.update {
            it.copy(
                height = _searchRequest.value.height.copy(
                    from = height
                )
            )
        }
    }

    fun onChangeHeightTo(height: String) {
        _searchRequest.update {
            it.copy(
                height = _searchRequest.value.height.copy(
                    to = height
                )
            )
        }
    }

    fun onChoiceSingleEnumUi(enum: EnumUi) {
        when (enum) {
            is DrinkingUi -> _searchRequest.update { it.copy(drinking = enum) }
            is SmokingUi -> _searchRequest.update { it.copy(smoking = enum) }
            is MaritalStatusUi -> _searchRequest.update { it.copy(maritalStatus = enum) }
            is IsHasChildrenUi -> _searchRequest.update { it.copy(children = enum) }
        }
    }

    fun addEnumUi(enum: EnumUi) {
        when (enum) {
            is GenderUi -> _searchRequest.update {
                it.copy(
                    gender = it.gender.addOrEmpty(
                        enum,
                        GenderUi.NULL
                    )
                )
            }

            is OrientationUi -> _searchRequest.update {
                it.copy(
                    orientation = it.orientation.addOrEmpty(
                        enum, OrientationUi.NULL
                    )
                )
            }

            is ReligionUi -> _searchRequest.update {
                it.copy(
                    religion = it.religion.addOrEmpty(
                        enum, ReligionUi.NULL
                    )
                )
            }

            is EyeColorUi -> _searchRequest.update {
                it.copy(
                    eyeColor = it.eyeColor.addOrEmpty(
                        enum, EyeColorUi.NULL
                    )
                )
            }

            is HairColorUi -> _searchRequest.update {
                it.copy(
                    hairColor = it.hairColor.addOrEmpty(
                        enum, HairColorUi.NULL
                    )
                )
            }

            is EducationUi -> _searchRequest.update {
                it.copy(
                    education = it.education.addOrEmpty(
                        enum,
                        EducationUi.NULL
                    )
                )
            }

            is PetUi -> _searchRequest.update { it.copy(pets = it.pets + enum) }
            is MusicGenreUi -> _searchRequest.update { it.copy(favoriteMusicGenres = it.favoriteMusicGenres + enum) }
        }
    }

    fun removeEnumUi(enum: EnumUi) {
        when (enum) {
            is GenderUi -> _searchRequest.update { it.copy(gender = it.gender - enum) }
            is OrientationUi -> _searchRequest.update { it.copy(orientation = it.orientation - enum) }
            is ReligionUi -> _searchRequest.update { it.copy(religion = it.religion - enum) }
            is EyeColorUi -> _searchRequest.update { it.copy(eyeColor = it.eyeColor - enum) }
            is HairColorUi -> _searchRequest.update { it.copy(hairColor = it.hairColor - enum) }
            is EducationUi -> _searchRequest.update { it.copy(education = it.education - enum) }
            is PetUi -> _searchRequest.update { it.copy(pets = it.pets - enum) }
            is MusicGenreUi -> _searchRequest.update { it.copy(favoriteMusicGenres = it.favoriteMusicGenres - enum) }
        }
    }

    fun saveSearchRequest() {
        viewModelScope.launch {
            searchUserRepository.saveSearchFilter(_searchRequest.value.toSearchUserRequest())
        }

    }

}

private inline fun <reified T : Enum<T>> List<T>.addOrEmpty(inputEnum: T, enumForNull: T): List<T> {
    return if (inputEnum == enumForNull) {
        listOf()
    } else {
        this + inputEnum
    }
}


private fun SearchUserRequestUi.toSearchUserRequest(): SearchUserFilterRequest {
    return SearchUserFilterRequest(
        city = city.takeIf { it.isNotBlank() },
        name = name.takeIf { it.isNotBlank() },
        gender = gender.takeIf { it.isNotEmpty() }?.mapNotNull { genderUi -> genderUi.toGender() },
        orientation = orientation.takeIf { it.isNotEmpty() }
            ?.mapNotNull { orientationUi -> orientationUi.toOrientation() },
        dateOfBirthRange = dateOfBirthRange.toDateOfBirthRange(),
        children = children.toBoolean(),
        religion = religion.takeIf { it.isNotEmpty() }
            ?.mapNotNull { religionUi -> religionUi.toReligion() },
        weight = weight.toWeightRange(),
        height = height.toHeightRange(),
        eyeColor = eyeColor.takeIf { it.isNotEmpty() }
            ?.mapNotNull { eyeColorUi -> eyeColorUi.toEyeColor() },
        hairColor = hairColor.takeIf { it.isNotEmpty() }
            ?.mapNotNull { hairColorUi -> hairColorUi.toHairColor() },
        education = education.takeIf { it.isNotEmpty() }
            ?.mapNotNull { educationUi -> educationUi.toEducation() },
        smoking = smoking.toSmoking(),
        drinking = drinking.toDrinking(),
        maritalStatus = maritalStatus.toMaritalStatus(),
        pets = pets.takeIf { it.isNotEmpty() }?.toPets(),
        favoriteMusicGenres = favoriteMusicGenres.takeIf { it.isNotEmpty() }
            ?.toFavoriteMusicGenres(),
        isOnline = isOnline
    )
}

private fun DateOfBirthRangeUi.toDateOfBirthRange(): DateOfBirthRange {
    return DateOfBirthRange(
        from = from.minDateOfBirthUnix(),
        to = to.maxDateOfBirthUnix()
    )
}

private fun WeightRangeUi.toWeightRange(): WeightRange {
    return WeightRange(
        from = from.toIntOrNull(),
        to = to.toIntOrNull()
    )
}

private fun HeightRangeUi.toHeightRange(): HeightRange {
    return HeightRange(
        from = from.toIntOrNull(),
        to = to.toIntOrNull()
    )
}


