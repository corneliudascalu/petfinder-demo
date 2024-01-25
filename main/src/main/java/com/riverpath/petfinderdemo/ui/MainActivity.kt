package com.riverpath.petfinderdemo.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riverpath.petfinder.features.ServiceLocator
import com.riverpath.petfinderdemo.ui.ui.theme.PetfinderDemoTheme

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PetfinderDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PetfinderUI(viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetfinderUI(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier,
    ) {
        SearchBar(
            placeholder = { Text(text = "Find a pet") },
            query = uiState.value.searchTerm ?: "",
            onQueryChange = { q -> viewModel.search(q) },
            onSearch = {},
            active = true,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "",
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = "")
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
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                ListItem(headlineContent = { Text(text = "test") },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = ""
                        )
                    },
                    supportingContent = { Text(text = "description") })
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {

                    viewModel.testAuth()
                }) {
                    Text(text = "Authenticate")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PetfinderDemoTheme {
        PetfinderUI(viewModel = MainViewModel())
    }
}