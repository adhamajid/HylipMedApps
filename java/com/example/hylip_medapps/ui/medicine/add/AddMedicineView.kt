package com.windranger.reminder.ui.medicine.add

import com.windranger.reminder.base.mvp.BaseView
import com.windranger.reminder.model.drug.Drug
import com.windranger.reminder.model.drug.DrugType

interface AddMedicineView : BaseView {
    fun updateDrugType(list: List<DrugType>)
    fun updateDrug(list: List<Drug>)
    fun closePage()
}