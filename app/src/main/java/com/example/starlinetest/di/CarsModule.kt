package com.example.starlinetest.di

import android.app.Application
import com.example.starlinetest.data.local.IMainRepo
import com.example.starlinetest.data.local.CarsRepo
import com.example.starlinetest.presentation.modules.cars.viewmodel.CarsVmFactory
import dagger.Module
import dagger.Provides

@Module
class CarsModule(private val application: Application) {

    @Provides
    fun provideCarsViewModelFactory(repo: IMainRepo): CarsVmFactory = CarsVmFactory(application, repo)

    @Provides
    fun provideCarsRepo(): IMainRepo = CarsRepo()

}