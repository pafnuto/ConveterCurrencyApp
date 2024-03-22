package com.example.currencyconvertapp

import ResponseData
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    val liveData = MutableLiveData<ResponseData>()
    val errorLiveData = MutableLiveData<String>()

    fun getData(curr1: String, curr2: String) {

        ApiClient.getClient().getResponse(curr1,curr2).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    liveData.postValue(responseBody)
                } else {
                    errorLiveData.postValue(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.d("onFailure","${t.message}")
                errorLiveData.postValue(t.message)
            }

        })
    }
}