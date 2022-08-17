package com.windranger.reminder.util.ext


val String?.value: String
    get() = this ?: ""