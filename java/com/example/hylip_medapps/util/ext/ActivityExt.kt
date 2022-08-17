package com.windranger.reminder.util.ext

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import com.windranger.reminder.R


inline fun <reified T : Any> Activity.launchActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_out);
}

inline fun <reified T : Any> Activity.launchActivity(
        requestCode: Int = -1,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode)
    overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_out);
}

inline fun <reified T : Any> Activity.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> Context.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)

inline fun <reified T : Any> Fragment.launchActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(activity!!)
    intent.init()
    startActivity(intent)
    activity!!.overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_out);
}

inline fun <reified T : Any> Fragment.launchActivity(
        requestCode: Int = -1,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(activity!!)
    intent.init()
    startActivityForResult(intent, requestCode)
    activity!!.overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_out);
}

inline fun <reified T : Any> Fragment.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(activity!!)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

fun Context.share(url: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, url)
    startActivity(Intent.createChooser(shareIntent, "Share link using"))
}

fun Activity.setOk() {
    setResult(RESULT_OK)
    finish()
    overridePendingTransition(R.anim.scale_in, R.anim.slide_out_to_right)
}

fun Activity.shareForResult(url: String, code: Int = 100){
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, url)
    startActivityForResult(Intent.createChooser(shareIntent, "Share link using"), code)
}