package com.pavlig43.features.common.enumui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pavlig43.features.common.UserInfoItemRow
import com.pavlig43.features.common.enumui.model.EnumUi
import com.pavlig43.features.common.enumui.model.OrientationUi
import kotlin.reflect.KClass


@Composable
 fun OneEnumItem(
    openedMenu: KClass<out EnumUi>?,
    enumItem: EnumUi,
    entries: List<EnumUi>,
    onOpenMenu: (KClass<out EnumUi>?) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onChoiceItem: (EnumUi) -> Unit,
    modifier: Modifier = Modifier
) {
    UserInfoItemRow(
        text = stringResource(enumItem.description),
        modifier = modifier
    ) {
        OneEnumItemDropMenu(
            openedMenu = openedMenu,
            onOpenMenu = onOpenMenu,
            entries = entries,
            onExpandedChange = onExpandedChange,
            enumItem = enumItem,
            onChoiceItem = onChoiceItem
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OneEnumItemDropMenu(
    openedMenu: KClass<out EnumUi>?,
    enumItem: EnumUi,
    entries: List<EnumUi>,
    onOpenMenu: (KClass<out EnumUi>?) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onChoiceItem: (EnumUi) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember(openedMenu) {
        mutableStateOf(openedMenu?.let { it == enumItem::class } == true)
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
                    if (expanded) onOpenMenu(null) else onOpenMenu(enumItem::class)
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(enumItem.translate), fontSize = FONT_SIZE.sp)
            Icon(
                if (!expanded) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "dropDown Menu"
            )

            DropdownMenu(expanded = expanded, onDismissRequest = { onOpenMenu(null) }) {

                entries.forEach { entry ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                stringResource(entry.translate),
                                textDecoration = TextDecoration.Underline
                            )
                        },
                        onClick = {
                            onChoiceItem(entry)
                            onOpenMenu(null)
                        },
                    )
                }

            }

        }

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


