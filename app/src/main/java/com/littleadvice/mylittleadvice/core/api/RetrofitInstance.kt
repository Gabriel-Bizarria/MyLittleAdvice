package com.littleadvice.mylittleadvice.core.api

import com.littleadvice.mylittleadvice.core.constants.ADVICES_BASE_URL
import com.littleadvice.mylittleadvice.core.utils.NetworkUtils

object RetrofitInstance{
    private val retrofitClient = NetworkUtils.getRetrofitInstance(ADVICES_BASE_URL)
    val api: ApiService = retrofitClient.create(ApiService::class.java)
}