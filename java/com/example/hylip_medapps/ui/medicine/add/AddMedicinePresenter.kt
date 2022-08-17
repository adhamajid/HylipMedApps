package com.windranger.reminder.ui.medicine.add

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.model.alarm.Alarm
import com.windranger.reminder.model.db.Medicine
import com.windranger.reminder.util.SessionManager
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AddMedicinePresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager, boxStore: BoxStore) :
        NetPresenter<AddMedicineView>(disposable, api, sessionManager) {

    private val medicineBox: Box<Medicine> = boxStore.boxFor()

    fun getDrugType() {
        disposable.add(api.getDrugType(sessionManager.token)
                .compose(singleSchedulers())
                .subscribe(
                        { view?.updateDrugType(it) },
                        this::processError
                )
        )
    }

    fun getDrug(id: Int) {
        disposable.add(api.getDrug(sessionManager.token, id)
                .compose(singleSchedulers())
                .subscribe(
                        { view?.updateDrug(it) },
                        this::processError
                )
        )
    }

    fun addAlarm(form: Alarm) {
        disposable.add(api.addAlarm(sessionManager.token, form)
                .compose(singleSchedulers())
                .subscribe(
                        {
                            medicineBox.put(it.toMedicineDb())
                            view?.closePage()
                        },
                        this::processError
                )
        )
    }

    fun editAlarm(id: Int, form: Alarm) {
        disposable.add(api.editAlarm(sessionManager.token, id, form)
                .compose(singleSchedulers())
                .subscribe(
                        {
                            medicineBox.put(it.toMedicineDb())
                            view?.closePage()
                        },
                        this::processError
                )
        )
    }

}