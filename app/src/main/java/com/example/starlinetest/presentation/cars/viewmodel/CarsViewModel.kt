package com.example.starlinetest.presentation.cars.viewmodel

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.starlinetest.R
import com.example.starlinetest.domain.entity.Car
import com.example.starlinetest.domain.entity.Main
import com.example.starlinetest.presentation.cars.adapter.CarsAdapter
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CarsViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private var disposable: Disposable? = null

    private val carsList: MutableLiveData<List<Car>> = MutableLiveData()

    fun getcarsList(): LiveData<List<Car>> = carsList

    fun parseFile() {
        disposable = Single.fromCallable { readJsonFromAssets() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                carsList.value = it?.devices?.list
            }, {
                showToast(it.message)
            })
    }

    private fun readJsonFromAssets(): Main? {
        val gson = Gson()
        val fileName = "cars.json"
        var inputStream: InputStream? = null
        var model: Main? = null
        var reader: InputStreamReader? = null
        val res = getApplication<Application>().resources
        try {
            val assets = res.assets
            inputStream = assets.open(fileName)
            reader = InputStreamReader(inputStream)
            model = gson.fromJson(reader, Main::class.java)

        } catch (e: IOException) {
            showToast(res.getString(R.string.file_err_read))
        } finally {
            reader?.close()
            inputStream?.close()
        }
        return model
    }

    private fun showToast(msg: String?) {
        msg?.let {
            Toast.makeText(getApplication(), it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

}