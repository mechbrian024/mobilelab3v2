package com.example.pokai.pokaichao_comp304sec001_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.ProductDatabase
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.ProductRepository
import com.example.pokai.pokaichao_comp304sec001_lab3.Screen.AppScreen
import com.example.pokai.pokaichao_comp304sec001_lab3.ui.theme.Pokaichao_COMP304Sec001_Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = ProductDatabase.getDatabase(this)
        val repository = ProductRepository(database.productDao())

        setContent {
            Pokaichao_COMP304Sec001_Lab3Theme {
                AppScreen(repository)
            }
        }
    }
}