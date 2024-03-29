package com.riverpath.petfinderdemo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.riverpath.petfinder.features.Features
import com.riverpath.petfinderdemo.R
import com.riverpath.petfinderdemo.ui.ui.theme.PetfinderDemoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            PetfinderDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "petList") {
                        composable("petList") {
                            // TODO Move the [PetfinderUI] composable to the [:petsearch] module too
                            PetfinderUI(
                                viewModel = viewModel,
                                onOpenDetails = { id -> navController.navigate("petDetails/$id") })
                        }
                        composable("petDetails/{petID}") {

                            // Let the app crash if we forget to pass the petID
                            val petID = it.arguments?.getString("petID")!!
                            Features.petDetails.PetDetailsUI(petID)
                        }
                        // Add more destinations similarly.
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetfinderUI(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onOpenDetails: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val uiStateValue = uiState.value as SearchUiState
    val primaryColor = MaterialTheme.colorScheme.primary

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.error.collect {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    Scaffold(
        modifier = modifier,
    ) {
        SearchBar(
            placeholder = { Text(text = "Find a pet") },
            query = uiStateValue.searchTerm ?: "",
            onQueryChange = { q -> viewModel.onSearchTermChanged(q) },
            onSearch = { q -> viewModel.search(q) },
            active = true,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "",
                )
            },
            trailingIcon = {
                when (uiStateValue) {
                    is SearchUiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.size(
                            24.dp
                        )
                    )

                    is SearchUiState.Searching -> Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.clear()
                            })
                }

            },
            colors = SearchBarDefaults.colors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = CircleShape.copy(all = CornerSize(24.dp))
                )
        ) {
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn {
                    items(count = uiStateValue.currentResults.size) { index ->
                        val animal = uiStateValue.currentResults[index]
                        ListItem(
                            modifier = Modifier.clickable {
                                onOpenDetails(animal.id)
                            },
                            headlineContent = { Text(text = animal.name) },
                            leadingContent = {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(animal.thumbnailURL)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = remember { ColorPainter(primaryColor) },
                                    error = rememberAsyncImagePainter(model = R.drawable.pets),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                )
                            },
                            supportingContent = { Text(text = animal.description) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PetfinderDemoTheme {
        PetfinderUI(viewModel = MainViewModel()) {}
    }
}