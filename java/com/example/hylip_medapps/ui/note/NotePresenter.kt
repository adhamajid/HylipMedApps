package com.windranger.reminder.ui.note

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.util.SessionManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NotePresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager) :
        NetPresenter<NoteView>(disposable, api, sessionManager) {

    fun getNotes() {
        disposable.add(api.getNotes(sessionManager.token)
                .compose(singleSchedulers())
                .subscribe(
                        { view?.updateList(it) },
                        this::processError
                )
        )
    }
}