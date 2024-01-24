package com.test.series.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import com.test.series.R
import com.test.series.dataclass.DetailsBasedata
import com.test.series.retrofit.network.ApiService
import com.test.series.util.ApiState
import com.test.series.viewmodel.DetailVieModel
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable fun DetailsPage(detailVieModel: DetailVieModel) {
    var isRefreshing by remember { mutableStateOf(false) }
    getData(detailVieModel)





}
@Composable fun getData(detailVieModel: DetailVieModel) {
    var isRefreshing by remember { mutableStateOf(false) }


    when (val result = detailVieModel.response.value) {

        is ApiState.Successdetails -> {
            DetailsPageCompose(result.data)

        }


        is ApiState.Failure -> {
            Text(text = "${result.msg}")
        }

        ApiState.Loading -> {
            ProgressBar()
            //CustomCircularProgressBar()
        }

        ApiState.NoNetwork -> {
            AlertDialog()
        }


    }
}
@Composable
fun AlertDialog() {
    var showDialog by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Custom AlertDialog
        if (showDialog) {
            androidx.compose.material.AlertDialog(
                onDismissRequest = {
                    // Handle dismiss if needed
                    showDialog = false
                },
                title = {
                    Text("No Internet")
                },
                text = {
                    Text("Kindly check your connections")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // Handle confirmation if needed
                            showDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {

                    // You can omit this if you don't need a dismiss button
                }
            )
        }
    }
}
@Composable
fun ProgressBar() {
    var progress by remember { mutableStateOf(0.5f) }

    // Simulate progress updates (replace with your actual logic)
    LaunchedEffect(Unit) {
        while (progress < 1.0f) {
            delay(100)
            progress += 0.02f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color.Blue,
            strokeWidth = 5.dp,
        )
        Text(
            text = "Progress: ${(progress * 100).roundToInt()}%",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}



@Composable
fun DetailsPageCompose(detailsBase: DetailsBasedata) {
    Box(modifier = Modifier)
    {
        DetailScreen(detailsBase = detailsBase)
    }}






@Composable
fun DetailScreen(detailsBase: DetailsBasedata) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // App Bar
        TopAppBar(
            title = { Text(text = detailsBase.original_name) },
            navigationIcon = {
                IconButton(onClick = {  }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = { /* Handle favorite action */ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                }
                IconButton(onClick = { /* Handle share action */ }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null)
                }
            }
        )

        // Movie Image
        Image(
            painter = rememberAsyncImagePainter(ApiService.ImageURL+detailsBase.backdrop_path),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .padding(2.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Movie Details
        Text(
            modifier = Modifier
                .padding(2.dp),
            text = "Genre: ${detailsBase.original_name}",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier
                .padding(2.dp),
            text = "First Air Date: ${detailsBase.first_air_date}",
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Movie Overview
        Text(
            modifier = Modifier
                .padding(2.dp),
            text = "Overview",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .padding(2.dp),
            text = detailsBase.overview)
    }
}
