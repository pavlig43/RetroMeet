package com.pavlig43.features.resume

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlig43.features.R
import com.pavlig43.features.common.date.DatePickerDocked
import com.pavlig43.features.common.enumui.model.DrinkingUi
import com.pavlig43.features.common.enumui.model.EducationUi
import com.pavlig43.features.common.enumui.model.EnumUi
import com.pavlig43.features.common.enumui.model.EyeColorUi
import com.pavlig43.features.common.enumui.model.GenderUi
import com.pavlig43.features.common.enumui.model.HairColorUi
import com.pavlig43.features.common.enumui.ListEnumItem
import com.pavlig43.features.common.enumui.model.MaritalStatusUi
import com.pavlig43.features.common.enumui.model.MusicGenreUi
import com.pavlig43.features.common.enumui.OneEnumItem
import com.pavlig43.features.common.enumui.model.OrientationUi
import com.pavlig43.features.common.enumui.model.PetUi
import com.pavlig43.features.common.enumui.model.ReligionUi
import com.pavlig43.features.common.enumui.model.SmokingUi
import com.pavlig43.features.common.UserInfoItem
import com.pavlig43.features.common.textfield.TextFieldResumeItem
import com.pavlig43.features.resume.model.ResumeUi
import com.pavlig43.retromeetuicommon.ErrorScreen
import com.pavlig43.retromeetuicommon.LoadingScreen
import kotlin.reflect.KClass

@Composable
fun ResumeScreen(
    returnMainScreen: (Int) -> Unit,
    viewModel: ResumeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val resumeState by viewModel.resumeState.collectAsState()
    val resumeUi by viewModel.resumeUi.collectAsState()
    when (val state = resumeState) {
        is ResumeState.Error -> ErrorScreen(state.errorMessage, modifier)
        is ResumeState.Loading -> LoadingScreen(modifier)
        is ResumeState.Success -> ResumeScreenPrivate(
            resumeUi = resumeUi,
            setDate = viewModel::setDate,
            changeName = viewModel::changeName,
            changeWeight = viewModel::changeWeight,
            changeHeight = viewModel::changeHeight,
            changeCountChildren = viewModel::changeCountChildren,
            onChoiceSingleEnum = viewModel::onChoiceSingleEnumUi,
            addSingleEnumUi = viewModel::addEnumUi,
            removeEnumUi = viewModel::removeEnumUi,
            updateResume = { viewModel.updateResume { returnMainScreen(it) } },
            modifier = modifier,
        )
    }

}

@Composable
private fun ResumeScreenPrivate(
    resumeUi: ResumeUi,
    setDate: (Long?) -> Unit,
    changeName: (String) -> Unit,
    changeWeight: (String) -> Unit,
    changeHeight: (String) -> Unit,
    changeCountChildren: (String) -> Unit,
    onChoiceSingleEnum: (EnumUi) -> Unit,
    addSingleEnumUi: (EnumUi) -> Unit,
    removeEnumUi: (EnumUi) -> Unit,
    updateResume: () -> Unit,
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
        TextFieldResumeItem(
            description = R.string.name,
            value = resumeUi.name,
            onValueChange = changeName
        )

        UserInfoItem(
            text = stringResource(R.string.date_of_birth),
            item = {
                DatePickerDocked(
                    date = resumeUi.dateOfBirth,
                    onDateSelected = setDate
                )
            }
        )

        TextFieldResumeItem(
            description = R.string.weight_kg,
            value = resumeUi.weight,
            onValueChange = changeWeight,
            maxChars = 3,
            keyboardType = KeyboardType.Number,
            isValidValueChange = { it.toIntOrNull() != null }
        )
        TextFieldResumeItem(
            description = R.string.height_cm,
            value = resumeUi.height,
            onValueChange = changeHeight,
            maxChars = 3,
            keyboardType = KeyboardType.Number,
            isValidValueChange = { it.toIntOrNull() != null }
        )
        TextFieldResumeItem(
            description = R.string.count_children,
            value = resumeUi.countChildren,
            onValueChange = changeCountChildren,
            maxChars = 2,
            keyboardType = KeyboardType.Number,
            isValidValueChange = { it.toIntOrNull() != null }
        )
        val singleEnumList: Map<EnumUi, List<EnumUi>> = mapOf(
            resumeUi.gender to GenderUi.entries.toList(),
            resumeUi.orientation to OrientationUi.entries.toList(),
            resumeUi.religion to ReligionUi.entries.toList(),
            resumeUi.eyeColor to EyeColorUi.entries.toList(),
            resumeUi.hairColor to HairColorUi.entries.toList(),
            resumeUi.education to EducationUi.entries.toList(),
            resumeUi.drinking to DrinkingUi.entries.toList(),
            resumeUi.smoking to SmokingUi.entries.toList(),
            resumeUi.maritalStatus to MaritalStatusUi.entries.toList()
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
        val multiEnumList: Map<List<EnumUi>, List<EnumUi>> = mapOf(
            resumeUi.pets to PetUi.entries.toList(),
            resumeUi.favoriteMusicGenres to MusicGenreUi.entries.toList()
        )
        multiEnumList.forEach { (enumItems: List<EnumUi>, entries: List<EnumUi>) ->
            ListEnumItem(
                openedMenu = openedMenu,
                onOpenMenu = { openedMenu = it },
                onExpandedChange = {},
                enumItems = enumItems,
                entries = entries,
                addEnumUi = addSingleEnumUi,
                removeEnumUi = removeEnumUi
            )
        }
        Button(updateResume) {
            Text(stringResource(R.string.save))
        }


    }
}


