package com.littleadvice.mylittleadvice.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.littleadvice.mylittleadvice.core.models.ResponseAdvice
import com.littleadvice.mylittleadvice.core.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _adviceLiveData = MutableLiveData<String>()
    val adviceLiveData: LiveData<String> = _adviceLiveData

    fun getAdvice(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getAdvice().enqueue(object : Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(!response.isSuccessful){
                        Log.e("RESPONSE_ERROR", "The response was not successful")
                    }else{
                        val adviceResponse: ResponseAdvice = Gson().fromJson(response.body(), ResponseAdvice::class.java)
                        val advice = adviceResponse.slip?.advice
                        _adviceLiveData.postValue(advice ?: "NULL_VALUE")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.v("CONTACT_FAILURE", "It was not possible to contact the API")
                }
            })
        }
    }

}