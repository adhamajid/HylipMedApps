package com.windranger.reminder.model.alarm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.windranger.reminder.model.db.Medicine
import com.windranger.reminder.util.ext.default
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
@Parcelize
data class Alarm(

        @field:SerializedName("consumed")
        val consumed: String? = null,

        @field:SerializedName("dosage")
        val dosage: String? = null,

        @field:SerializedName("drug_id")
        val drugId: String? = null,

        @field:SerializedName("consumed_time")
        val consumedTime: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("drug_type_id")
        val drugTypeId: String? = null,

        @field:SerializedName("times")
        val times: List<Times>? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("repeat")
        val repeat: String? = "daily",

        @field:SerializedName("qty")
        val qty: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("other_name")
        val otherName: String? = null,

        @field:SerializedName("id")
        val id: Int? = null
) : Parcelable {
    fun toMedicineDb(): Medicine {
        var timeStr = ""
        times?.forEachIndexed { index, time ->
            timeStr += if (index != 0) ",${time.time}" else time.time
        }

        return Medicine(id = id.default.toLong(), drugTypeId = drugTypeId.default.toInt(),
                drugId = drugId.default.toInt(), name = name.default, otherName = otherName.default,
                qty = qty.default.toInt(), consumed = consumed.default, consumedTime = consumedTime.default,
                dosis = dosage.default, times = timeStr)
    }
}