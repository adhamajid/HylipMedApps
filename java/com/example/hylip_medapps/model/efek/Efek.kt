package com.windranger.reminder.model.efek

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
@Parcelize
data class Efek(

        @field:SerializedName("effect")
        val effect: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("how_to_use")
        val howToUse: String? = null
) : Parcelable