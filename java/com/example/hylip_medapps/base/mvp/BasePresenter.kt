package com.windranger.reminder.base.mvp

import com.windranger.reminder.api.Api
import com.windranger.reminder.util.SessionManager
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T>(protected val disposable: CompositeDisposable,
                                protected val api: Api,
                                protected val sessionManager: SessionManager) {

    protected var view: T? = null

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        disposable.dispose()
        view = null
    }

    companion object {
        const val SOMETHING_WRONG = "Something went wrong"
        const val CLIENT_ID = "2"
        const val CLIENT_SECRET = "B8lqNO7x1qjlPZA3j4b7DXJXi6qMMSv3DsiWNasd"
    }
}