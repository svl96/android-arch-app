package com.yandex.android.mynotesandroid.data.remote

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import android.util.Log

class AuthService(private val context : Context) {

    companion object {
        const val TAG = "AuthService"
        const val KEY_CLIENT_ID = "clientId"
        const val KEY_CLIENT_SECRET = "clientSecret"
        const val ACCOUNT_TYPE = "com.yandex.passport"
    }

    private val clientId = "0d0970774e284fa8ba9ff70b6b06479a"
    private val clientSecret = "a7ee508b75664955bd5d5ad59f66b196"

    private var authToken : String? = null

    init {
        generateToken()
    }

    private fun generateToken() {
        val accountManager = AccountManager.get(context)

        val yandexAccounts = accountManager.getAccountsByType(ACCOUNT_TYPE)
        val acc : Account = yandexAccounts.first { acc -> acc.name.startsWith("ser") } ?: return

        Log.d(TAG, acc.name)

        val options = Bundle()
        options.putString(KEY_CLIENT_ID, clientId)
        options.putString(KEY_CLIENT_SECRET, clientSecret)

        try {
            accountManager.getAuthToken(acc, clientId, options, true,
                    { bundle ->
                        try {
                            val token = bundle.result.getString(AccountManager.KEY_AUTHTOKEN)
                            Log.d(TAG, token)
                            authToken = token
                        } catch (ex: Exception) {
                            Log.e("Exception", ex.message)
                        }
                    },
                    null
                    )

        } catch (ex: Exception) {
            Log.e(TAG, ex.message)
        }
    }

    fun getToken() : String? {
        return authToken
    }

}
