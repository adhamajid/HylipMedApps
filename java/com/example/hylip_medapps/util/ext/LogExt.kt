package com.windranger.reminder.util.ext

import timber.log.Timber

fun Any.called(from: String) {
    Timber.d("$from: Called")
}