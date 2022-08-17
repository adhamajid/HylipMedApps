package com.windranger.reminder.base.mvp

import com.windranger.reminder.api.Api
import com.windranger.reminder.util.SessionManager
import io.reactivex.CompletableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by didik on 07/12/17.
 */
abstract class NetPresenter<T : BaseView>(disposable: CompositeDisposable, api: Api,
                                          sessionManager: SessionManager)
    : BasePresenter<T>(disposable, api, sessionManager) {

    protected fun <V> singleSchedulers(): SingleTransformer<V, V> {
        return SingleTransformer<V, V> { upstream ->
            upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { view?.showLoading() }
                    .doAfterTerminate { view?.hideLoading() }
        }
    }

    protected fun completeableSchedulers(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { view?.showLoading() }
                    .doAfterTerminate { view?.hideLoading() }
        }
    }

    protected fun processError(e: Throwable) {
        view?.showMessage(fetchErrorMessage(e))
    }

    protected fun fetchErrorMessage(e: Throwable): String {
        return when (e) {
            is HttpException -> {
                val responseBody = e.response().errorBody()
                getErrorMessage(responseBody!!)
            }
            is SocketTimeoutException -> "Connection timed out."
            is IOException -> "Connection lost, please check your connection."
            else -> e.message ?: "Error"
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.message ?: ""
        }
    }

    protected fun createImageBody(file: File, key: String = "file"): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData(key, file.name, requestFile)
    }

    protected fun createTextBody(text: String): RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), text)
}