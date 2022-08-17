package com.windranger.reminder.ui.main

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.model.alarm.Alarm
import com.windranger.reminder.model.db.Medicine
import com.windranger.reminder.model.db.MyAlarm
import com.windranger.reminder.model.terapi.Terapi
import com.windranger.reminder.util.SessionManager
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainPresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager, boxStore: BoxStore) :
        NetPresenter<MainView>(disposable, api, sessionManager) {

    private var list = mutableListOf<Alarm>()
    private val medicineBox: Box<Medicine> = boxStore.boxFor()
    private val alarmBox: Box<MyAlarm> = boxStore.boxFor()
    private val terapiBox: Box<Terapi> = boxStore.boxFor()

    fun getTerapi() {
        disposable.add(api.getTerapi(sessionManager.token)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        {
                            terapiBox.removeAll()
                            terapiBox.put(it)
                        },
                        { Timber.e(it) }
                )
        )
    }

    fun getAlarm() {
        val dbs = medicineBox.all
        Timber.d("Size: ${dbs.size}")
        if (dbs.isNotEmpty()) {
            list.clear()
            dbs.mapTo(list) { it.toAlarm() }
            view?.updateList(list)
        }

        disposable.add(api.getAlarm(sessionManager.token)
                .compose(singleSchedulers())
                .doAfterSuccess { getTerapi() }
                .subscribe(
                        { it ->
                            list = it.toMutableList()
                            val db = mutableListOf<Medicine>()
                            list.mapTo(db) { it.toMedicineDb() }

                            medicineBox.removeAll()
                            medicineBox.put(db)
                            view?.updateList(it)
                        },
                        {
                            if (dbs.isEmpty()) processError(it)
                        }
                )
        )
    }

    fun delete(id: Int, pos: Int) {
        disposable.add(api.deleteAlarm(sessionManager.token, id)
                .compose(completeableSchedulers())
                .subscribe(
                        {
                            medicineBox.remove(id.toLong())
                            list.removeAt(pos)
                            view?.updateList(list)
                        },
                        this::processError
                )
        )
    }

    fun clearDb() {
        medicineBox.removeAll()
        alarmBox.removeAll()
    }
}