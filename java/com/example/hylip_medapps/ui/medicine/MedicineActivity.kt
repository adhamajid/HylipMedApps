package com.windranger.reminder.ui.medicine

import android.os.Bundle
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.ui.medicine.add.AddMedicineActivity
import com.windranger.reminder.util.ext.launchActivity
import kotlinx.android.synthetic.main.activity_medicine.*

class MedicineActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { launchActivity<AddMedicineActivity>() }

        /*rvMedicine.layoutManager = LinearLayoutManager(this)
        rvMedicine.adapter = MedicineAdapter()*/
    }
}