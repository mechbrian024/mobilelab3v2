package com.example.pokai.pokaichao_comp304sec001_lab3.di

import com.example.pokai.pokaichao_comp304sec001_lab3.Database.ProductRepositoryImpl
import org.koin.dsl.module
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.ProductRepository

val appModules = module {
    single<ProductRepository> { ProductRepositoryImpl(get())}
}
