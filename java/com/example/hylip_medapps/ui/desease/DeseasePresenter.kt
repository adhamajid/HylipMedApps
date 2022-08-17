package com.windranger.reminder.ui.desease

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.model.desease.form.DeseaseForm
import com.windranger.reminder.util.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeseasePresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager) :
        NetPresenter<DeseaseView>(disposable, api, sessionManager) {

    fun getDesease() {
        disposable.add(api.getDesease()
                .compose(singleSchedulers())
                .subscribe(
                        { view?.updateList(it) },
                        this::processError
                )
        )
    }

    fun submitDesease(form: DeseaseForm) {
        disposable.add(api.submitDesease(sessionManager.token, form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoadingDialog() }
                .doAfterTerminate { view?.hideLoadingDialog() }
                .subscribe(
                        { view?.openQuestionairePage() },
                        this::processError
                )
        )
    }
}