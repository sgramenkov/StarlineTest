package com.example.starlinetest.di

import com.example.starlinetest.presentation.modules.cars.view.CarsFragment
import com.example.starlinetest.presentation.modules.map.view.MapFragment
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [CarsModule::class])
interface CarComponent {
    fun inject(fragment: CarsFragment)
}


