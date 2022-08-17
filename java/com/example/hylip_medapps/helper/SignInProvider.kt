package com.windranger.reminder.helper

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.windranger.reminder.model.UserModel
import timber.log.Timber


class SignInProvider(private val mContext: Context) : GoogleApiClient.OnConnectionFailedListener {

    private var mGoogleApiClient: GoogleApiClient? = null

    init {
        setupGoogleClient()
    }

    // Setup Google Client
    private fun setupGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(mContext)
                .enableAutoManage(mContext as AppCompatActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    // [START signIn]
    fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        (mContext as AppCompatActivity).startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signIn]

    // [START signOut]
    fun signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
            Timber.d("Sign Out")
        }
    }
    // [END signOut]

    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Timber.d("onConnectionFailed:$connectionResult")
    }

    fun handleActivityResult(requestCode: Int, data: Intent,
                             onSuccess: (account: UserModel) -> Unit,
                             onFailed: () -> Unit) {

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Timber.d("handleSignInResult:${result.isSuccess}")
            when {
                result.isSuccess -> {
                    // Signed in successfully, show authenticated UI.
                    val acct = result.signInAccount
                    val name = acct?.displayName ?: ""
                    val email = acct?.email ?: ""
                    val id = acct?.id ?: ""
                    val googleAccount = UserModel(id, name, email)
                    onSuccess(googleAccount)
                }
                result.status.isCanceled -> Timber.d("Canceled")
                else -> onFailed()
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}