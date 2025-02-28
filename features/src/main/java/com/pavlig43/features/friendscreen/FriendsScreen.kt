package com.pavlig43.features.friendscreen

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.pavlig43.features.common.enumui.model.FriendStatusUi
import com.pavlig43.features.searchusersresult.SearchUsersResultScreenInternal
import kotlinx.coroutines.launch

@Composable
fun FriendsScreen(
    onUserScreen: (Int) -> Unit,
    viewModel: FriendViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val result = viewModel.result.collectAsLazyPagingItems()

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {

        val tabs = FriendStatusUi.entries.filter { it != FriendStatusUi.NO_DATA }
        val scope = rememberCoroutineScope()


        val pagerState = rememberPagerState { tabs.size }


        Log.d("pagerState", pagerState.currentPage.toString())


        LaunchedEffect(pagerState.currentPage) {
            viewModel.changeTab(tabs[pagerState.currentPage])
        }

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = { Spacer(Modifier.padding(8.dp)) }
        ) {
            tabs.forEachIndexed { index, status ->

                Tab(
                    text = { Text(stringResource(status.translate)) },
                    selected = index == pagerState.currentPage,
                    onClick = {
                        viewModel.changeTab(tabs[index])
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )

            }
        }

        HorizontalPager(pagerState,
            modifier = Modifier.fillMaxSize()) {

            SearchUsersResultScreenInternal(onUserScreen, result, Modifier.fillMaxSize())
        }


    }

}


