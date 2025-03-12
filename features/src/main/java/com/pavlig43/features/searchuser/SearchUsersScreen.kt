package com.pavlig43.features.searchuser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlig43.features.R
import com.pavlig43.features.common.UserInfoItemRow
import com.pavlig43.features.common.enumui.ListEnumItem
import com.pavlig43.features.common.enumui.OneEnumItem
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
import com.pavlig43.features.common.textfield.TextFieldRange
import com.pavlig43.features.common.textfield.TextFieldResumeItem
import com.pavlig43.features.searchuser.model.SearchUserRequestUi
import kotlin.reflect.KClass



@Composable
fun SearchUsersScreen(
    onSearchScreen: () -> Unit,
    viewModel: SearchUsersViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val request by viewModel.searchRequest.collectAsState()
    SearchUsersScreenPrivate(
        searchRequest = request,
        changeName = viewModel::changeName,
        changeCity = viewModel::changeCity,
        onValueChangeAgeFrom = viewModel::onChangeAgeFrom,
        onValueChangeAgeTo = viewModel::onChangeAgeTo,
        onValueChangeWeightFrom = viewModel::onChangeWeightFrom,
        onValueChangeWeightTo = viewModel::onChangeWeightTo,
        onValueChangeHeightFrom = viewModel::onChangeHeightFrom,
        onValueChangeHeightTo = viewModel::onChangeHeightTo,
        onChoiceSingleEnum = viewModel::onChoiceSingleEnumUi,
        addEnumUi = viewModel::addEnumUi,
        removeEnumUi = viewModel::removeEnumUi,
        onSearch = {
            viewModel.saveSearchRequest()
            onSearchScreen()
        },
        onOnlyOnlineChange = viewModel::onChangeOnline,
        modifier = modifier,
    )

}

@Composable
fun SearchUsersScreenPrivate(
    searchRequest: SearchUserRequestUi,
    onOnlyOnlineChange: (Boolean) -> Unit,
    changeName: (String) -> Unit,
    changeCity: (String) -> Unit,
    onValueChangeAgeFrom: (String) -> Unit,
    onValueChangeAgeTo: (String) -> Unit,
    onValueChangeWeightFrom: (String) -> Unit,
    onValueChangeWeightTo: (String) -> Unit,
    onValueChangeHeightFrom: (String) -> Unit,
    onValueChangeHeightTo: (String) -> Unit,
    onChoiceSingleEnum: (EnumUi) -> Unit,
    addEnumUi: (EnumUi) -> Unit,
    removeEnumUi: (EnumUi) -> Unit,
    onSearch:()-> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var openedMenu: KClass<out EnumUi>? by remember { mutableStateOf(null) }
        UserInfoItemRow(stringResource(R.string.only_online)){
            Checkbox(
                checked = searchRequest.isOnline,
                onCheckedChange = onOnlyOnlineChange
            )
        }
        TextFieldResumeItem(
            description = R.string.name,
            value = searchRequest.name,
            onValueChange = changeName
        )
        TextFieldRange(
            description = R.string.age,
            valueFrom = searchRequest.dateOfBirthRange.from,
            valueTo = searchRequest.dateOfBirthRange.to,
            onValueChangeFrom = onValueChangeAgeFrom,
            onValueChangeTo = onValueChangeAgeTo

        )
        TextFieldResumeItem(
            description = R.string.city,
            value = searchRequest.city,
            onValueChange = changeCity
        )
        TextFieldRange(
            description = R.string.weight_kg,
            valueFrom = searchRequest.weight.from,
            valueTo = searchRequest.weight.to,
            onValueChangeFrom = onValueChangeWeightFrom,
            onValueChangeTo = onValueChangeWeightTo,
            maxCharsTo = 3,
            maxCharsFrom = 3
        )
        TextFieldRange(
            description = R.string.height_cm,
            valueFrom = searchRequest.height.from,
            valueTo = searchRequest.height.to,
            onValueChangeFrom = onValueChangeHeightFrom,
            onValueChangeTo = onValueChangeHeightTo,
            maxCharsTo = 3,
            maxCharsFrom = 3
        )
        val multiEnumList:Map<List<EnumUi>, List<EnumUi>> = mapOf(
            GenderUi.entries.toList() to searchRequest.gender,
            OrientationUi.entries.toList() to searchRequest.orientation,
            ReligionUi.entries.toList() to searchRequest.religion,
            EyeColorUi.entries.toList() to searchRequest.eyeColor,
            HairColorUi.entries.toList() to searchRequest.hairColor,
            EducationUi.entries.toList() to searchRequest.education,
            PetUi.entries.toList() to searchRequest.pets,
            MusicGenreUi.entries.toList() to searchRequest.favoriteMusicGenres
        )

        multiEnumList.forEach { (entries: List<EnumUi>, enumItems: List<EnumUi>) ->
            ListEnumItem(
                openedMenu = openedMenu,
                onOpenMenu = { openedMenu = it },
                onExpandedChange = {},
                enumItems = enumItems,
                entries = entries,
                addEnumUi = addEnumUi,
                removeEnumUi = removeEnumUi
            )}
        val singleEnumList: Map<EnumUi, List<EnumUi>> = mapOf(

            searchRequest.children to IsHasChildrenUi.entries.toList(),
            searchRequest.drinking to DrinkingUi.entries.toList(),
            searchRequest.smoking to SmokingUi.entries.toList(),
            searchRequest.maritalStatus to MaritalStatusUi.entries.toList()
        )
        singleEnumList.forEach { (enumItem: EnumUi, entries: List<EnumUi>) ->
            OneEnumItem(
                openedMenu = openedMenu,
                onOpenMenu = { openedMenu = it },
                onExpandedChange = {},
                enumItem = enumItem,
                entries = entries,
                onChoiceItem = onChoiceSingleEnum
            )
        }


        Button(onSearch) {
            Text(stringResource(R.string.search))
        }



    }

}

@Preview(showBackground = true)
@Composable
private fun SearchUsersScreenPrev() {
    SearchUsersScreenPrivate(
        searchRequest = SearchUserRequestUi(isOnline = true),
        changeName = {},
        onValueChangeAgeFrom = {},
        onValueChangeAgeTo = {},
        modifier = Modifier,
        changeCity = {},
        onValueChangeWeightFrom = {},
        onValueChangeWeightTo = {},
        onValueChangeHeightFrom = {},
        onValueChangeHeightTo = {},
        onChoiceSingleEnum = {},
        addEnumUi = {},
        removeEnumUi = {},
        onSearch = {},
        onOnlyOnlineChange = {}
    )

}
