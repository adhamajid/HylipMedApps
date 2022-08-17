package com.windranger.reminder.ui.efek

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.util.SessionManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class EfekPresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager) :
        NetPresenter<EfekView>(disposable, api, sessionManager) {

    fun getEfekSamping() {
        disposable.add(api.getEfekSamping(sessionManager.token)
                .compose(singleSchedulers())
                .subscribe(
                        { view?.updateList(it) },
                        this::processError
                )
        )
    }
}