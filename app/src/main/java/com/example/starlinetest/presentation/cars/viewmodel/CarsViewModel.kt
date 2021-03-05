package com.example.starlinetest.presentation.cars.viewmodel

import android.content.res.AssetManager
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starlinetest.domain.entity.Main
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class CarsViewModel : ViewModel() {

    private val data = MutableLiveData<Main>()

    fun getCarsLiveData(): LiveData<Main> = data

    fun setCars(item: Main?){
        item?.let {
            data.value=it
        }
    }

    val msgObservable: ObservableField<String> = ObservableField()

    fun readJsonFromAssets(assets: AssetManager): Main? {
        val gson = Gson()
        val fileName = "cars.json"
        var inputStream: InputStream? = null
        var model: Main? = null
        var reader: InputStreamReader? = null
        try {
            inputStream = assets.open(fileName)
            reader = InputStreamReader(inputStream)
            model = gson.fromJson(reader, Main::class.java)

        } catch (e: IOException) {
            msgObservable.set("Error reading file")
        } finally {
            reader?.close()
            inputStream?.close()
        }
        return model
    }
}