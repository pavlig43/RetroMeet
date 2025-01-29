package com.pavlig43.retromeetuicommon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage

@Composable
fun Photo(
    path: String? = null,
    @DrawableRes pathNullHolder: Int = R.drawable.nofoto,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = path ?: pathNullHolder,
        contentDescription = stringResource(R.string.avatar_image),
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PhotoPreview() {
    Photo()
}
