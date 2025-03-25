package com.example.pokai.pokaichao_comp304sec001_lab3

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokai.pokaichao_comp304sec001_lab3.Screen.EditProductScreen
import com.example.pokai.pokaichao_comp304sec001_lab3.Screen.HomeScreen
import com.example.pokai.pokaichao_comp304sec001_lab3.Screen.NewProductScreen
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModel

//All screens
enum class ScreenEnum(@StringRes val title: Int) {
    Home(title = R.string.screen_home),
    Product(title = R.string.screen_product)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController, viewModel: ProductViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("addProduct") { NewProductScreen(navController, viewModel) }
        composable("editProduct/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            productId?.let { EditProductScreen(navController, viewModel, productId) }
        }
    }
}
