package com.example.starlinetest.presentation.base

import android.app.Application
import com.example.starlinetest.di.CarComponent
import com.example.starlinetest.di.CarsModule
import com.example.starlinetest.di.DaggerCarComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerCarComponent.builder().carsModule(CarsModule(this)).build()
    }

    companion object {
        private lateinit var component: CarComponent

        fun getComponent(): CarComponent = component
    }
}