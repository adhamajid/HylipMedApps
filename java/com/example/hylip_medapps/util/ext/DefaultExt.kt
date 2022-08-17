package com.windranger.reminder.util.ext

val String?.default: String
    get() = this ?: ""

val Int?.default: Int
    get() = this ?: 0

val Long?.default: Long
    get() = this ?: 0