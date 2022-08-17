package com.windranger.reminder.ui.efek

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.efek.Efek
import com.windranger.reminder.util.ext.launchActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_efek.*
import javax.inject.Inject

class EfekActivity : ToolbarActivity(), EfekView {

    @Inject lateinit var presenter: EfekPresenter

    private val adapter by lazy { EfekAdapter { openDetail(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efek)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        rvEfek.layoutManager = LinearLayoutManager(this)
        rvEfek.adapter = adapter

        presenter.attachView(this)

        swipeRefresh.setOnRefreshListener { presenter.getEfekSamping() }
        swipeRefresh.post { presenter.getEfekSamping() }
    }

    override fun updateList(list: List<Efek>) {
        adapter.submitList(list)
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showMessage(message: String) {
        showPopup(message)
    }

    private fun openDetail(pos: Int) {
        launchActivity<EfekDetailActivity> {
            putExtra(EXTRAS_DATA, adapter.getObject(pos))
        }
    }
}