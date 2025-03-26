package com.example.pokai.pokaichao_comp304sec001_lab3.Database

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insert(product: Product)
    suspend fun update(product: Product)
    suspend fun delete(product: Product)
    fun getProductById(productId: Int): Flow<Product?>
}