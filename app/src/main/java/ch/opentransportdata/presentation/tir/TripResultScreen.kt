package ch.opentransportdata.presentation.tir

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ch.opentransportdata.presentation.reachedBottom
import ch.opentransportdata.presentation.reachedTop
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Composable
fun ResultScreenComposable() {
    val coroutineScope = rememberCoroutineScope()
    val elements = remember { mutableListOf(1, 2, 3, 4, 5, 6, 7) }
    var isLoadingPrevious by remember { mutableStateOf(false) }
    var isLoadingNext by remember { mutableStateOf(false) }
    var previousItemsCount by remember { mutableIntStateOf(0) }


    TripResultScreen(
        elements = elements,
        loadPrevious = {
            Log.d("MainActivity", "Start loading previous elements")
            coroutineScope.launch {
                isLoadingPrevious = true
                delay(2000)
                val newItems = List(5) { Random.nextInt(10, 1000) }
                elements.addAll(0, newItems)
                previousItemsCount = 5
                isLoadingPrevious = false
            }
        },
        isLoadingPrevious = isLoadingPrevious,
        previousItemsCount = previousItemsCount,
        loadNext = {
            Log.d("MainActivity", "Start loading next elements")
            coroutineScope.launch {
                isLoadingNext = true
                delay(2000)
                val newItems = List(5) { Random.nextInt(10, 1000) }
                elements.addAll(newItems)
                isLoadingNext = false
            }
        },
    )
}

@Composable
fun TripResultScreen(
    elements: List<Int>,
    loadPrevious: () -> Unit,
    isLoadingPrevious: Boolean,
    previousItemsCount: Int,
    loadNext: () -> Unit,
) {
    val listState = rememberLazyListState()
    val reachedBottom by remember { derivedStateOf { listState.reachedBottom() } } //todo: check if these can be useful to simplify logic
    val reachedTop by remember { derivedStateOf { listState.reachedTop() } }
    var initialItemsLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(elements.isNotEmpty()) {
        //scrolls to first element position (not the loading indicator)
        if (elements.isNotEmpty()) listState.scrollToItem(1)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                if (layoutInfo.visibleItemsInfo.isNotEmpty()) {
                    val firstVisibleItemIndex = layoutInfo.visibleItemsInfo.first().index
                    val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.last().index

                    if (initialItemsLoaded && firstVisibleItemIndex <= 1 && !isLoadingPrevious) {
                        loadPrevious()
                    }

                    if (listState.isScrollInProgress && !initialItemsLoaded) {
                        initialItemsLoaded = true
                    }

                    if (lastVisibleItemIndex >= layoutInfo.totalItemsCount - 2) {
                        loadNext()
                    }
                }
            }
    }

    //todo: sometimes has issues with scrolling (not resetting correctly and load indefinitely previous items)
    LaunchedEffect(previousItemsCount) {
        if (previousItemsCount > 0) {
            listState.scrollToItem(index = previousItemsCount)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Text(text = "Header not yet implemented!", style = MaterialTheme.typography.titleLarge)


            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Loading indicator for previous items
                item {
                    CircularProgressIndicator()
                }

                items(elements) { item ->
                    TripItem(startWithWalkLeg = false, hasDisruptions = false, numberOfTransferLegs = item)
                    HorizontalDivider()
                }

                // Loading indicator for next items
                item {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TripItemPreview() {
    OJPAndroidSDKTheme {
        TripResultScreen(
            elements = listOf(1, 2, 3, 4, 5),
            isLoadingPrevious = false,
            loadPrevious = {},
            previousItemsCount = 0,
            loadNext = {}
        )
    }
}