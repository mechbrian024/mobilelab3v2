package com.example.pokai.pokaichao_comp304sec001_lab3.Screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.pokai.pokaichao_comp304sec001_lab3.AppNavigation
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.ProductRepository
import com.example.pokai.pokaichao_comp304sec001_lab3.R
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModel

//All screens
enum class Screen(@StringRes val title: Int) {
    Home(title = R.string.screen_home),
    Product(title = R.string.screen_product)
}

@Composable
fun AppScreen(
    productRepository: ProductRepository,
) {
    val navController = rememberNavController()
    val viewModel = ProductViewModel(productRepository)
    AppNavigation(navController, viewModel)
}