package com.littleadvice.mylittleadvice.core.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/advice")
    fun getAdvice() : Call<JsonObject>

}