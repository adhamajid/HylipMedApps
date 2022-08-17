package com.windranger.reminder.ui.desease

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.ToolbarActivity
import com.windranger.reminder.model.desease.Desease
import com.windranger.reminder.ui.questionaire.QuestionaireActivity
import com.windranger.reminder.util.ext.launchActivity
import com.windranger.reminder.util.ext.toast
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_desease.*
import javax.inject.Inject

class DeseaseActivity : ToolbarActivity(), DeseaseView {

    @Inject lateinit var presenter: DeseasePresenter

    private val adapter by lazy { DeseaseAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desease)

        mActionBar.setDisplayHomeAsUpEnabled(true)

        btnSave.setOnClickListener { submitDesease() }

        initRecycler()

        presenter.attachView(this)

        swipeRefresh.setOnRefreshListener { presenter.getDesease() }
        swipeRefresh.post { presenter.getDesease() }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun updateList(list: List<Desease>) {
        adapter.submitList(list)
    }

    override fun openQuestionairePage() {
        launchActivity<QuestionaireActivity>()
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

    override fun showLoadingDialog() {
        showLoadingBar()
    }

    override fun hideLoadingDialog() {
        hideLoadingBar()
    }

    private fun initRecycler() {
        rvDesease.layoutManager = LinearLayoutManager(this)
        rvDesease.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvDesease.adapter = adapter
    }

    private fun submitDesease() {
        val form = adapter.getForm()
        if (form.disease != null && form.disease.isEmpty()) {
            toast("Mohon pilih penyakit")
            return
        }

        presenter.submitDesease(form)
    }
}