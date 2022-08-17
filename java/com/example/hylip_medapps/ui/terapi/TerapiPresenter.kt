package com.windranger.reminder.ui.terapi

import com.windranger.reminder.api.Api
import com.windranger.reminder.base.mvp.NetPresenter
import com.windranger.reminder.model.terapi.Terapi
import com.windranger.reminder.util.SessionManager
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class TerapiPresenter
@Inject constructor(disposable: CompositeDisposable, api: Api, sessionManager: SessionManager, boxStore: BoxStore) :
        NetPresenter<TerapiView>(disposable, api, sessionManager) {

    private val terapiBox: Box<Terapi> = boxStore.boxFor()

    fun getTerapi() {
        val db = terapiBox.all
        Timber.d("Size: ${db.size}")
        if (db.isNotEmpty()) view?.updateList(db)

        disposable.add(api.getTerapi(sessionManager.token)
                .compose(singleSchedulers())
                .subscribe(
                        { data ->
                            val list = data.sortedBy { it.id }
                            terapiBox.removeAll()
                            terapiBox.put(list)
                            view?.updateList(list)
                        },
                        { if (db.isEmpty()) processError(it) }
                )
        )
    }
}