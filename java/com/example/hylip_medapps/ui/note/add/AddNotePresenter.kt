package com.windranger.reminder.ui.note.add

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.util.SessionManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AddNotePresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager) :
        NetPresenter<AddNoteView>(disposable, api, sessionManager) {

    fun add(title: String, content: String) {
        disposable.add(api.addNote(sessionManager.token, title, content)
                .compose(completeableSchedulers())
                .subscribe(
                        { view?.closePage() },
                        this::processError
                )
        )
    }

    fun edit(id: Int, title: String, content: String) {
        disposable.add(api.editNote(id, sessionManager.token, title, content)
                .compose(completeableSchedulers())
                .subscribe(
                        { view?.closePage() },
                        this::processError
                )
        )
    }

    fun delete(id: Int) {
        disposable.add(api.deleteNote(id, sessionManager.token)
                .compose(completeableSchedulers())
                .subscribe(
                        { view?.closePage() },
                        this::processError
                )
        )
    }
}