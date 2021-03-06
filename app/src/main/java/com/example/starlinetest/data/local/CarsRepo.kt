package com.example.starlinetest.data.local

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.starlinetest.R
import com.example.starlinetest.domain.entity.DataCompletion
import com.example.starlinetest.domain.entity.Main
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class CarsRepo : IMainRepo {

    private var disposable: Disposable? = null

    override fun getJsonFromFile(assets: AssetManager, liveData: MutableLiveData<DataCompletion<Main?>>) {
        val dataCompletion = DataCompletion<Main?>()
        disposable = Single.fromCallable {
            val gson = Gson()
            val fileName = "cars.json"
            var inputStream: InputStream? = null
            val model: Main?
            var reader: InputStreamReader? = null

            try {
                inputStream = assets.open(fileName)
                reader = InputStreamReader(inputStream)
                model = gson.fromJson(reader, Main::class.java)
                dataCompletion.data = model
            } catch (e: IOException) {
                dataCompletion.error = e.message
            } finally {
                reader?.close()
                inputStream?.close()
            }

        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                liveData.value = dataCompletion
            }, {
                liveData.value = dataCompletion
            })

    }

    override fun onDestroy() {
        disposable?.dispose()
    }
}