package com.example.starlinetest.di

import com.example.starlinetest.presentation.cars.view.CarsFragment
import com.example.starlinetest.presentation.map.view.MapFragment
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Component(modules = [CarsModule::class])
interface CarComponent {
    fun addModule(module: MapModule): MapComponent
    fun inject(fragment: CarsFragment)
}

@Subcomponent(modules = [MapModule::class])
interface MapComponent {
    fun inject(frag: MapFragment)
}

