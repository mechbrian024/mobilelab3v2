package com.example.pokai.pokaichao_comp304sec001_lab3

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokai.pokaichao_comp304sec001_lab3.Screen.HomeScreen
import com.example.pokai.pokaichao_comp304sec001_lab3.Screen.NewProductScreen
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController, viewModel: ProductViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("addProduct") { NewProductScreen(navController, viewModel) }
//        composable("productDetail/{productId}") { backStackEntry ->
//            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
//            productId?.let { ProductDetailScreen(navController, viewModel, it) }
    }
}
