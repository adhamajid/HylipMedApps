package com.windranger.reminder.ui.terapi

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.terapi.Terapi
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_terapi.*
import javax.inject.Inject

class TerapiActivity : ToolbarActivity(), TerapiView {

    @Inject lateinit var presenter: TerapiPresenter

    private val adapter by lazy { TerapiAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terapi)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        rvTerapi.layoutManager = LinearLayoutManager(this)
        rvTerapi.adapter = adapter

        presenter.attachView(this)

        swipeRefresh.setOnRefreshListener { presenter.getTerapi() }
        swipeRefresh.post { presenter.getTerapi() }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun updateList(list: List<Terapi>) {
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

}