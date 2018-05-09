package com.yandex.android.mynotesandroid

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.os.Bundle
import android.test.suitebuilder.annotation.Smoke
import com.yandex.android.mynotesandroid.data.remote.AuthService
import com.yandex.android.mynotesandroid.data.remote.AuthServiceImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AuthServiceTest {


    @Test
    fun getToken_callAccountManager() {
        val accManager = Mockito.mock(AccountManager::class.java)

        val account = Account("ser.test", "com.type.test")
        Mockito.`when`(accManager.getAccountsByType(Mockito.anyString()))
                .thenReturn(arrayOf(account))


        val authService = AuthServiceImpl(accManager)
        authService.getToken()

        Mockito.verify(accManager).getAuthToken(account, Mockito.anyString(),
                Mockito.any<Bundle>(), Mockito.anyBoolean(), Mockito.any<AccountManagerCallback<Bundle>>(),
                null)
    }



}