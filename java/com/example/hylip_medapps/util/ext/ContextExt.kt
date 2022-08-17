package com.windranger.reminder.util.ext

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * Created by didik on 08/12/17.
 */

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.toast(@StringRes resId: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(resId), length).show()
}
