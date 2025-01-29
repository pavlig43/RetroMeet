package com.pavlig43.features.searchuser

import androidx.lifecycle.ViewModel
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
import com.pavlig43.features.searchuser.model.SearchUserRequestUi
import com.pavlig43.retromeetdata.searchuserRepository.SearchUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class SearchUsersViewModel @Inject constructor(
    private val searchUserRepository: SearchUserRepository
) : ViewModel() {


    private val _searchRequest = MutableStateFlow(SearchUserRequestUi())
    val searchRequest = _searchRequest.asStateFlow()


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

    fun onChangeWeightFrom(weight: String){
        _searchRequest.update {
            it.copy(
                weight = _searchRequest.value.weight.copy(
                    from = weight
                )
            )
        }
    }
    fun onChangeWeightTo(weight: String){
        _searchRequest.update {
            it.copy(
                weight = _searchRequest.value.weight.copy(
                    to = weight
                )
            )
        }
    }

    fun onChangeHeightFrom(height: String){
        _searchRequest.update {
            it.copy(
                height = _searchRequest.value.height.copy(
                    from = height
                )
            )
        }
    }
    fun onChangeHeightTo(height: String){
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
            is GenderUi -> _searchRequest.update { it.copy(gender = it.gender + enum) }
            is OrientationUi -> _searchRequest.update { it.copy(orientation = it.orientation + enum) }
            is ReligionUi -> _searchRequest.update { it.copy(religion = it.religion + enum) }
            is EyeColorUi -> _searchRequest.update { it.copy(eyeColor = it.eyeColor + enum) }
            is HairColorUi -> _searchRequest.update { it.copy(hairColor = it.hairColor + enum) }
            is EducationUi -> _searchRequest.update { it.copy(education = it.education + enum) }
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

}
sealed interface SearchState{
    data object CreateSearch: SearchState
    data object ResultSearch: SearchState
}
