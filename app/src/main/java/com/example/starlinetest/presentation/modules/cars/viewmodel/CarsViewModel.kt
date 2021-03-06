package com.example.starlinetest.presentation.modules.cars.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.starlinetest.data.local.IMainRepo
import com.example.starlinetest.domain.entity.DataCompletion
import com.example.starlinetest.domain.entity.Main
import javax.inject.Inject

class CarsViewModel @Inject constructor(application: Application, private val repo: IMainRepo) :
    AndroidViewModel(application) {

    private val carsList: MutableLiveData<DataCompletion<Main?>> = MutableLiveData()


    fun getCarsList(): LiveData<DataCompletion<Main?>> = carsList

    fun parseFile() {
        repo.getJsonFromFile(getApplication<Application>().resources.assets, carsList)
    }

    override fun onCleared() {
        repo.onDestroy()
        super.onCleared()
    }

}