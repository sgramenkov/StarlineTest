package com.example.starlinetest

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.starlinetest.di.CarComponent
import com.example.starlinetest.di.DaggerCarComponent
import com.example.starlinetest.di.MapModule
import com.example.starlinetest.presentation.map.view.MapFragment

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        builder = DaggerCarComponent.builder()
    }


    companion object {
        private lateinit var builder: DaggerCarComponent.Builder

        fun getBuilder(): DaggerCarComponent.Builder = builder
    }
}