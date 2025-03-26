package com.example.pokai.pokaichao_comp304sec001_lab3.Screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.pokai.pokaichao_comp304sec001_lab3.AppNavigation
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.ProductRepositoryImpl
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModel
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModelFactory

@Composable
fun AppScreen(
    productRepository: ProductRepositoryImpl,
    context: Context
) {
//    val navController = rememberNavController()
//    val viewModel = ProductViewModel(productRepository)

//    val viewModel = remember { ProductViewModel(productRepository) }
//    val navController = rememberNavController()

    val viewModel: ProductViewModel = viewModel(
        factory = ProductViewModelFactory(productRepository)
    )
    val navController = rememberNavController()

    AppNavigation(
        navController, viewModel, context
    )
}