package com.example.starlinetest.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.starlinetest.presentation.map.viewmodel.MapViewModel
import dagger.Module
import dagger.Provides

@Module
class MapModule(val frag: Fragment) {

    @Provides
    fun provideMapViewModel(): MapViewModel =
        ViewModelProvider(frag).get(MapViewModel::class.java)
}