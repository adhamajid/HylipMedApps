package com.windranger.reminder.ui.efek

import android.os.Bundle
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.efek.Efek
import kotlinx.android.synthetic.main.activity_efek_detail.*

class EfekDetailActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efek_detail)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra<Efek>(EXTRAS_DATA)

        tvTitle.text = "Mekanisme Kerja ${data.name}"
        tvDesc.text = data.description
        tvEfek.text = data.effect
    }
}