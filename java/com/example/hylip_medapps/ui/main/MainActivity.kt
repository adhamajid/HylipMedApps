package com.windranger.reminder.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.TextView
import com.windranger.reminder.R
import com.windranger.reminder.base.mvp.BaseActivity
import com.windranger.reminder.model.alarm.Alarm
import com.windranger.reminder.service.cancelAlarm
import com.windranger.reminder.ui.alarm.AlarmActivity
import com.windranger.reminder.ui.auth.login.LoginActivity
import com.windranger.reminder.ui.efek.EfekActivity
import com.windranger.reminder.ui.medicine.MedicineActivity
import com.windranger.reminder.ui.medicine.MedicineAdapter
import com.windranger.reminder.ui.medicine.add.AddMedicineActivity
import com.windranger.reminder.ui.note.NoteActivity
import com.windranger.reminder.ui.questionaire.QuestionaireActivity
import com.windranger.reminder.ui.terapi.TerapiActivity
import com.windranger.reminder.util.SessionManager
import com.windranger.reminder.util.ext.default
import com.windranger.reminder.util.ext.launchActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.didik.ioswidget.dialog.IOSDialog
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, NavigationView.OnNavigationItemSelectedListener {

    @Inject lateinit var sessionManager: SessionManager
    @Inject lateinit var presenter: MainPresenter

    private val adapter by lazy { MedicineAdapter(listener) }

    private val deleteDialog by lazy { IOSDialog(this) }
    private var selectedPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { launchActivity<AddMedicineActivity>(100) }

        setupNav()

        initRecycler()
        initDeleteDialog()

        presenter.attachView(this)

        swipeRefresh.setOnRefreshListener { presenter.getAlarm() }
        swipeRefresh.post { presenter.getAlarm() }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            swipeRefresh.post { presenter.getAlarm() }
        }
    }

    override fun updateList(list: List<Alarm>) {
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

    private fun setupNav() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val header = nav_view.getHeaderView(0)
        val name = header.findViewById<TextView>(R.id.tvName)
        val email = header.findViewById<TextView>(R.id.tvEmail)

        name.text = sessionManager.name
        email.text = sessionManager.email
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_dashboard -> drawer_layout.closeDrawer(GravityCompat.START)
            R.id.nav_medicine -> launchActivity<MedicineActivity>()
            R.id.nav_terapi -> launchActivity<TerapiActivity>()
            R.id.nav_questionaire -> {
                launchActivity<QuestionaireActivity> {
                    putExtra(EXTRAS_DATA, QuestionaireActivity.QUESTIONAIRE)
                }
            }
            R.id.nav_reminder -> launchActivity<AlarmActivity>()
            R.id.nav_logout -> logout()
            R.id.nav_questionaire_app -> {
                launchActivity<QuestionaireActivity> {
                    putExtra(EXTRAS_DATA, QuestionaireActivity.APP_QUEST)
                }
            }
            R.id.nav_side_effect -> launchActivity<EfekActivity>()
            R.id.nav_note -> launchActivity<NoteActivity>()
        }

        //drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        sessionManager.clear()
        presenter.clearDb()
        cancelAlarm(applicationContext)
        launchActivity<LoginActivity> {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        finish()
    }

    private fun initRecycler() {
        rvReminder.layoutManager = LinearLayoutManager(this)
        rvReminder.adapter = adapter
    }

    private fun initDeleteDialog() {
        deleteDialog.setTitle("Menghapus Alarm")
        deleteDialog.setSubtitle("Apakah Anda yakin ingin menghapus alarm ini?")
        deleteDialog.setNegativeLabel("Batal")
        deleteDialog.setPositiveLabel("Hapus")
        deleteDialog.setNegativeListener { it.dismiss() }
        deleteDialog.setPositiveListener {
            it.dismiss()
            presenter.delete(adapter.getObject(selectedPos).id.default, selectedPos)
        }
    }

    private val listener = object : MedicineAdapter.MedicineListener {
        override fun onEdit(position: Int) {
            openEdit(position)
        }

        override fun onDelete(position: Int) {
            selectedPos = position
            deleteDialog.show()
        }

    }

    private fun openEdit(position: Int) {
        launchActivity<AddMedicineActivity>(100) {
            putExtra(EXTRAS_DATA, adapter.getObject(position))
        }
    }

}
