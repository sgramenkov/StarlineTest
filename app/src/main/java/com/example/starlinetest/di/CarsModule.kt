package com.example.starlinetest.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.starlinetest.presentation.cars.viewmodel.CarsViewModel
import com.example.starlinetest.presentation.map.viewmodel.MapViewModel
import dagger.Module
import dagger.Provides

@Module
class CarsModule(val frag: Fragment) {

    @Provides
    fun provideCarsViewModel(): CarsViewModel =
        ViewModelProvider(frag).get(CarsViewModel::class.java)

}