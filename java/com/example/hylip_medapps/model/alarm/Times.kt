package com.windranger.reminder.model.alarm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
@Parcelize
data class Times(

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("time")
        val time: String? = null
) : Parcelable