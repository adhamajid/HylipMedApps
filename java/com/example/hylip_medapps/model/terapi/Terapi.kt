package com.windranger.reminder.model.terapi

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
@Entity
data class Terapi(

        @field:SerializedName("disease")
        var disease: String? = null,

        @field:SerializedName("created_at")
        var createdAt: String? = null,

        @field:SerializedName("id")
        @Id(assignable = true)
        var id: Long = 0,

        @field:SerializedName("title")
        var title: String? = null,

        @field:SerializedName("content")
        var content: String? = null
)