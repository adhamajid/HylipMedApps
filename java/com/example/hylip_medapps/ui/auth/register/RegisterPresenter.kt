package com.windranger.reminder.ui.auth.register

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.util.SessionManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RegisterPresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager) :
        NetPresenter<RegisterView>(disposable, api, sessionManager) {

    fun register(email: String, name: String, pass: String, gender: String) {
        disposable.add(api.register(email, name, pass, gender)
                .compose(singleSchedulers())
                .subscribe(
                        {
                            sessionManager.setUser(it)
                            view?.openDashboard()
                        },
                        this::processError
                )
        )
    }
}