package com.example.starlinetest.presentation.modules.cars.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.starlinetest.data.local.IMainRepo

@Suppress("UNCHECKED_CAST")
class CarsVmFactory(val application: Application, val repo: IMainRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CarsViewModel(application, repo) as T
    }
}