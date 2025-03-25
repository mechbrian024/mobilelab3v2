package com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.Product
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts: StateFlow<List<Product>> = _favoriteProducts.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allProducts.collect { _allProducts.value = it }
        }
        viewModelScope.launch {
            repository.favoriteProducts.collect { _favoriteProducts.value = it }
        }
    }

    fun insert(product: Product) = viewModelScope.launch { repository.insert(product) }
    fun update(product: Product) = viewModelScope.launch { repository.update(product) }
    fun delete(product: Product) = viewModelScope.launch { repository.delete(product) }
}