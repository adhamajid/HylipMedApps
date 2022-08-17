package com.windranger.reminder.ui.auth.login

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.util.SessionManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginPresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager) :
        NetPresenter<LoginView>(disposable, api, sessionManager) {


    fun login(email: String, pass: String) {
        disposable.add(api.login(email, pass)
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

    fun loginWithGoogle(email: String, name: String, id: String) {
        disposable.add(api.loginWithGoogle(email, name, id)
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