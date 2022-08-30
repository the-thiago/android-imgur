package com.thiago.imgur.presentation.images

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thiago.core.domain.model.Image

@Composable
fun ImagesScreen(viewModel: ImagesViewModel = hiltViewModel()) {
//    val images = viewModel.imagesPagingData().collectAsLazyPagingItems()
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(images) {
//            it?.let { Image(image = it) }
//        }
//    }
    Text(text = "WIP")
}

@Composable
private fun Image(image: Image) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.url)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    )
}