package com.thiago.imgur.presentation.images

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.thiago.core.domain.model.Image
import com.thiago.imgur.R

@Composable
fun ImagesScreen(viewModel: ImagesViewModel = hiltViewModel()) {
    val images = viewModel.imagesPagingData().collectAsLazyPagingItems()
    ImagesScreen(images = images)
}

@Composable
private fun ImagesScreen(images: LazyPagingItems<Image>) {
    Column(modifier = Modifier.fillMaxSize()) {
        val isRefreshing = images.loadState.mediator?.refresh is LoadState.Loading
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { images.refresh() },
            indicator = { refreshState, trigger ->
                SwipeRefreshIndicator(
                    state = refreshState,
                    refreshTriggerDistance = trigger,
                    contentColor = MaterialTheme.colors.primary
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                (images.loadState.source.refresh is LoadState.NotLoading ||
                        images.loadState.mediator?.refresh is LoadState.NotLoading) -> {
                    ImagesLazyVerticalGrid(images = images)
                }
                isRefreshing -> ImagesPlaceholder()
                images.loadState.mediator?.refresh is LoadState.Error -> {
                    LoadingImagesError(onTryAgainClick = images::refresh)
                }
                images.loadState.refresh is LoadState.Error && images.itemCount == 0 -> {
                    LoadingImagesError(onTryAgainClick = images::refresh)
                }
            }
        }
    }
}

@Composable
private fun ImagesLazyVerticalGrid(images: LazyPagingItems<Image>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        item(span = { GridItemSpan(4) }) {
            HeaderLoadRemoteData(images = images)
        }
        items(images) {
            it?.let { Image(image = it) }
        }
        item(span = { GridItemSpan(4) }) {
            LoadingIndicator(
                isLoading = images.loadState.append == LoadState.Loading
            )
            AppendImagesError(
                showError = images.loadState.append is LoadState.Error,
                onTryAgainClick = images::retry
            )
        }
    }
}

@Composable
private fun HeaderLoadRemoteData(images: LazyPagingItems<Image>) {
    if (images.loadState.mediator?.refresh is LoadState.Error && images.itemCount > 0) {
        Text(
            modifier = Modifier
                .clickable { images.refresh() }
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            text = stringResource(id = R.string.error_refreshing_data),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoadingIndicator(isLoading: Boolean) {
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}

@Composable
private fun AppendImagesError(showError: Boolean, onTryAgainClick: () -> Unit) {
    if (showError) {
        Text(
            modifier = Modifier
                .clickable { onTryAgainClick() }
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            text = stringResource(id = R.string.error_loading_more_try_again),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoadingImagesError(onTryAgainClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_dissatisfied),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.images_loading_error),
            textAlign = TextAlign.Center,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onTryAgainClick() }) {
            Text(text = stringResource(id = R.string.images_try_again))
        }
    }
}

@Composable
private fun Image(image: Image) {
    val context = LocalContext.current
    val imageLoader by remember(image) {
        val loader = if (image.isGif) {
            ImageLoader.Builder(context)
                .components {
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()
        } else {
            ImageLoader.Builder(context).build()
        }
        mutableStateOf(loader)
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.url)
            .build(),
        imageLoader = imageLoader,
        contentDescription = image.description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(144.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(2.dp))
    )
}

@Composable
private fun ImagesPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(state = rememberScrollState(), enabled = false),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(6) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(4) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .height(144.dp)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                                shape = RoundedCornerShape(2.dp)
                            )
                            .clip(RoundedCornerShape(2.dp))
                    )
                }
            }
        }
    }
}

private fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                PagingPlaceholderKey(index)
            } else {
                key(item)
            }
        }
    ) { index ->
        itemContent(items[index])
    }
}

@SuppressLint("BanParcelableUsage")
private data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}