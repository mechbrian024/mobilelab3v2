package com.example.pokai.pokaichao_comp304sec001_lab3

import android.app.Application
import com.example.pokai.pokaichao_comp304sec001_lab3.di.appModules
import org.koin.core.context.startKoin

class ProductApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(appModules)
        }
    }
}