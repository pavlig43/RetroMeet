package com.pavlig43.features.common.enumui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pavlig43.features.R
import com.pavlig43.features.common.UserInfoItemRow
import com.pavlig43.features.common.enumui.model.EnumUi
import com.pavlig43.features.common.enumui.model.OrientationUi
import kotlin.reflect.KClass

@Composable
fun ListEnumItem(
    openedMenu: KClass<out EnumUi>?,
    enumItems: List<EnumUi>,
    entries: List<EnumUi>,
    onOpenMenu: (KClass<out EnumUi>?) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    addEnumUi: (EnumUi) -> Unit,
    removeEnumUi: (EnumUi) -> Unit,
    modifier: Modifier = Modifier
) {
    UserInfoItemRow(
        text = stringResource(entries.first().description),
        modifier = modifier
    ) {
        ListEnumItemDropMenu(
            openedMenu = openedMenu,
            onOpenMenu = onOpenMenu,
            entries = entries,
            onExpandedChange = onExpandedChange,
            enumItems = enumItems,
            addEnumUi = addEnumUi,
            removeEnumUi = removeEnumUi
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListEnumItemDropMenu(
    openedMenu: KClass<out EnumUi>?,
    enumItems: List<EnumUi>,
    entries: List<EnumUi>,
    onOpenMenu: (KClass<out EnumUi>?) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    addEnumUi: (EnumUi) -> Unit,
    removeEnumUi: (EnumUi) -> Unit,
    modifier: Modifier = Modifier
) {

    val expanded by remember(openedMenu) {
        mutableStateOf(openedMenu?.let { it == entries.first()::class } == true)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(expanded) },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .clickable {
                    if (expanded) onOpenMenu(null) else onOpenMenu(entries.first()::class)
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val items = enumItems.map { stringResource(it.translate) }.joinToString("\n")
                .takeIf { it.isNotEmpty() } ?: stringResource(
                R.string.not_important
            )
            Text(items, fontSize = FONT_SIZE.sp, textDecoration = TextDecoration.Underline)
            Icon(
                if (!expanded) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "dropDown Menu"
            )

            DropdownMenu(expanded = expanded, onDismissRequest = { onOpenMenu(null) }) {

                entries.forEach { entry ->
                    val checked = entry in enumItems
                    DropdownMenuItem(
                        text = {
                            CheckBoxRow(
                                enumUi = entry,
                                checked = checked,
                                addEnumUi = addEnumUi,
                                removeEnumUi = removeEnumUi

                            )
                        },
                        onClick = { if (!checked) addEnumUi(entry) else removeEnumUi(entry) }
                    )
                }

            }

        }

    }
}

@Composable
fun CheckBoxRow(
    enumUi: EnumUi,
    checked: Boolean,
    addEnumUi: (EnumUi) -> Unit,
    removeEnumUi: (EnumUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = { check -> if (check) addEnumUi(enumUi) else removeEnumUi(enumUi) }
        )
        Spacer(Modifier.weight(1f))
        Text(stringResource(enumUi.translate), textDecoration = TextDecoration.Underline)
    }
}

private const val FONT_SIZE = 22

@Preview(showBackground = true)
@Composable
private fun EnumItemPreview() {
    OneEnumItem(
        openedMenu = null,
        onOpenMenu = {},
        onExpandedChange = {},
        enumItem = OrientationUi.TRADITIONAL,
        entries = OrientationUi.entries.toList(),
        onChoiceItem = {}
    )

}
