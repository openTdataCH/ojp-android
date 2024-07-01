package ch.opentransportdata.presentation

import androidx.compose.foundation.lazy.LazyListState

/**
 * Created by Michael Ruppen on 28.06.2024
 */
fun LazyListState.reachedBottom(buffer: Int = 1): Boolean{
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}

fun LazyListState.reachedTop(): Boolean{
    val firstVisibleItem = this.layoutInfo.visibleItemsInfo.firstOrNull()
    return firstVisibleItem?.index?.let { it <= 1 } == true
}