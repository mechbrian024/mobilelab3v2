package com.example.pokai.pokaichao_comp304sec001_lab3.Screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.pokai.pokaichao_comp304sec001_lab3.AppNavigation
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.ProductRepository
import com.example.pokai.pokaichao_comp304sec001_lab3.R
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModel
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModelFactory

@Composable
fun AppScreen(
    productRepository: ProductRepository,
) {
//    val navController = rememberNavController()
//    val viewModel = ProductViewModel(productRepository)

//    val viewModel = remember { ProductViewModel(productRepository) }
//    val navController = rememberNavController()

    val viewModel: ProductViewModel = viewModel(
        factory = ProductViewModelFactory(productRepository)
    )
    val navController = rememberNavController()

    AppNavigation(navController, viewModel)
}