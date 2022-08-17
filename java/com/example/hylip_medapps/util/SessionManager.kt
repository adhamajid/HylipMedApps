package com.windranger.reminder.util

import android.content.Context
import android.content.SharedPreferences
import com.windranger.reminder.model.login.User
import com.windranger.reminder.util.ext.default

class SessionManager(context: Context) {
    // Shared Preferences
    private val pref: SharedPreferences

    private val editor: SharedPreferences.Editor

    init {
        pref = context.getSharedPreferences(PREF_NAME, 0)
        editor = pref.edit()
    }

    var id: Int
        get() = pref.getInt(ID, 0)
        set(value) = editor.putInt(ID, value).apply()

    var token: String
        get() = pref.getString(TOKEN, "")
        set(value) = editor.putString(TOKEN, "Bearer $value").apply()

    var isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGGED_IN, false)
        set(value) = editor.putBoolean(IS_LOGGED_IN, value).apply()

    var name: String
        get() = pref.getString(NAME, "")
        set(value) = editor.putString(NAME, value).apply()

    var email: String
        get() = pref.getString(EMAIL, "")
        set(value) = editor.putString(EMAIL, value).apply()

    var role: String
        get() = pref.getString(ROLE, "")
        set(value) = editor.putString(ROLE, value).apply()

    var gender: String
        get() = pref.getString(GENDER, "")
        set(value) = editor.putString(GENDER, value).apply()

    fun setUser(user: User) {
        id = user.id.default
        token = user.meta?.apiToken.default
        name = user.name.default
        isLoggedIn = true
        email = user.email.default
        role = user.role.default
        gender = user.gender.default
    }


    fun clear() {
        editor.clear()
        editor.commit()
    }

    companion object {
        private const val PREF_NAME = "SessionManager"
        private const val ID = "id"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val TOKEN = "token"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val ROLE = "role"
        private const val GENDER = "gender"
    }
}