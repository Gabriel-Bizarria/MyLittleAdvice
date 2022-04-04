package com.littleadvice.mylittleadvice.core.models

import com.google.gson.annotations.SerializedName

data class ResponseAdvice(
	@field:SerializedName("slip")
	val slip: Slip? = null
)

data class Slip(

	@field:SerializedName("advice")
	val advice: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
