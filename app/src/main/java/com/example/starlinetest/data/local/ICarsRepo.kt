package com.example.starlinetest.data.local

import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import com.example.starlinetest.domain.entity.DataCompletion
import com.example.starlinetest.domain.entity.Main

interface IMainRepo {
    fun getJsonFromFile(assets: AssetManager, liveData: MutableLiveData<DataCompletion<Main?>>)
    fun onDestroy()
}