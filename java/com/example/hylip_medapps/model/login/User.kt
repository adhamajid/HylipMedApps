package com.windranger.reminder.model.login

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class User(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null
)