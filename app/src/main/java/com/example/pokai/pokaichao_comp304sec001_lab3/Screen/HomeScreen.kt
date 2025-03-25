package com.example.pokai.pokaichao_comp304sec001_lab3.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.Product
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: ProductViewModel) {
    var showFavoritesOnly by remember { mutableStateOf(false) }

    val productsState =
        if (showFavoritesOnly) viewModel.favoriteProducts.collectAsStateWithLifecycle(initialValue = emptyList())
        else viewModel.allProducts.collectAsStateWithLifecycle(initialValue = emptyList())

    val products = productsState.value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Products") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addProduct") }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)//without this, app crash
                    .padding(12.dp)
            ) {
                itemsIndexed(items = products) { index, product ->
                    ProductListItem(product = product, navController = navController)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Button(onClick = { showFavoritesOnly = false }) {
                    Text("Show All Products")
                }

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center)
                {
                    Button(onClick = { showFavoritesOnly = true }) {
                        Text("Show Favorites")
                    }
                }
            }
        }

    }
}

@Composable
fun ProductListItem(product: Product, navController: NavController) {
    val cardSize: Dp = 100.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardSize)
            .padding(16.dp)
            .clickable {
                navController.navigate("editProduct/${product.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = product.category,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = product.dateOfDelivery.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }


    }
}