package com.windranger.reminder.model.desease.form

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class DeseaseForm(

	@field:SerializedName("disease")
	val disease: List<DeseaseItem>? = null
)