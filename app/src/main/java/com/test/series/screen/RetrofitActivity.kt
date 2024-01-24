package com.test.series.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.series.dataclass.ResultsPopularSeries
import com.test.series.retrofit.di.AppModule.iD
import com.test.series.ui.theme.ComponentsComposeTheme
import com.test.series.util.ApiState
import com.test.series.viewmodel.MainViewModel
import com.test.series.viewmodel.SharedViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.test.series.viewmodel.DetailVieModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@AndroidEntryPoint
class RetrofitActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val detailVieModel:DetailVieModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComponentsComposeTheme {
                val navController= rememberNavController();
                Surface(color = MaterialTheme.colors.background) {
                   // GETData(mainViewModel)


                    ComposeNavigation(mainViewModel)

                }
            }
        }
    }

//
//    @Composable fun EachRow(post: ResultsPopularSeries) {
//        Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(
//                        horizontal = 8.dp,
//                        vertical = 8.dp
//                    ),
//                elevation = 2.dp,
//                shape = RoundedCornerShape(4.dp)
//        ) {
//            post.name.let {
//                Text(
//                    text = it,
//                    modifier = Modifier.padding(10.dp)
//                )
//            }
//            post.vote_count.let {
//                Text(
//                    text = it.toString(),
//                    modifier = Modifier.padding(10.dp)
//                )
//            }
//        }
//
//    }

    @Composable
    fun CardWithImage(post: ResultsPopularSeries, navController: NavHostController) {
        val viewModel = viewModel<SharedViewModel>()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            onClick = {
                viewModel.intValue=post.id

                iD(post.id)
                // Handle card click if needed
                navController.navigate("Detailpage")

            }
        ) {
            Column {
                // Image
                Image(
                    painter = painterResource(id =com.test.series.R.drawable.dummy_girl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                    contentScale = ContentScale.Crop
                )

                // First Text
                Text(
                    text = post.name,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(16.dp)
                )

                // Second Text
                Text(
                    text = "Voted Count"+" : "+post.vote_count.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

    }



    @Composable
    fun swipeRefresh(mainViewModel: MainViewModel, navController: NavHostController){
        var refreshing by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(refreshing){
            if (refreshing){
                delay(3000)
                refreshing = false
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle favorite action */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }
            )
            SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = refreshing) , onRefresh = { refreshing=true })
            {
                GETData(mainViewModel = mainViewModel,navController)
            }
        }



    }

    @Composable fun GETData(mainViewModel: MainViewModel, navController: NavHostController) {
        var isRefreshing by remember { mutableStateOf(false) }


        when (val result = mainViewModel.response.value) {

            is ApiState.Success -> {


                LazyColumn {
                    items(result.data.results) { response ->
                        CardWithImage(post = response as ResultsPopularSeries,navController)
                    }
                }
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
                AlertDialog(
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
    fun ComposeNavigation(mainViewModel: MainViewModel) {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "First"){
            composable("First"){
                swipeRefresh(mainViewModel = mainViewModel,navController)
            }
            composable("Detailpage"){
                DetailsPage(detailVieModel)

            }
        }

    }

}
