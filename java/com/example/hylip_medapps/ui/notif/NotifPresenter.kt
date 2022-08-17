package com.windranger.reminder.ui.notif

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.model.db.Medicine
import com.windranger.reminder.model.db.Medicine_
import com.windranger.reminder.util.SessionManager
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NotifPresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager, boxStore: BoxStore) :
        NetPresenter<NotifView>(disposable, api, sessionManager) {

    private val medicineBox: Box<Medicine> = boxStore.boxFor()
    private val list = mutableListOf<Medicine>()

    fun searchData(qry: String) {
        val query = medicineBox.query().contains(Medicine_.times, qry).build().find()
        list.addAll(query)

        if (list.isNotEmpty()) view?.setMedicine(list[0])
    }

    fun takeMedicine() {
        if (list.isNotEmpty()) {
            val id = list[0].id.toInt()
            disposable.add(api.takeMedicine(sessionManager.token, id)
                    .compose(completeableSchedulers())
                    .subscribe(
                            {
                                list.removeAt(0)
                                if (list.isEmpty()) view?.openDashboard()
                                else view?.setMedicine(list[0])
                            },
                            this::processError
                    )
            )
        }
    }

    fun dismissMedicine() {
        if (list.isNotEmpty()) {
            list.removeAt(0)
            if (list.isEmpty()) view?.openDashboard()
            else view?.setMedicine(list[0])
        } else view?.openDashboard()
    }
}