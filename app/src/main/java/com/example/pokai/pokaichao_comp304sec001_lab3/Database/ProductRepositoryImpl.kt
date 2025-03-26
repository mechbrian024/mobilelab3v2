package com.example.pokai.pokaichao_comp304sec001_lab3.Database

import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()
    val favoriteProducts: Flow<List<Product>> = productDao.getFavoriteProducts()

    override suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    override suspend fun update(product: Product) {
        productDao.update(product)
    }

    override suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    override fun getProductById(productId: Int): Flow<Product?> {
        return productDao.getProductById(productId)
    }
}