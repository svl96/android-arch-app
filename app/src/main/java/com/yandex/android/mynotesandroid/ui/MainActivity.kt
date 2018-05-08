package com.yandex.android.mynotesandroid.ui

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.yandex.android.mynotesandroid.R
import com.yandex.android.mynotesandroid.service.AccessToken
import com.yandex.android.mynotesandroid.service.LoginService

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if ( supportFragmentManager.findFragmentByTag(NotesFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container,
                            NotesFragment.getInstance(),
                            NotesFragment.TAG)
                    .commit()
        }
        token()

    }

    private fun token() {
        val clientId = "0d0970774e284fa8ba9ff70b6b06479a"
        val clientSecret = "a7ee508b75664955bd5d5ad59f66b196"
        val callback = "https://oauth.yandex.ru/verification_code"
        val uri = intent.data
        if (uri != null && uri.toString().startsWith(callback)) {
            Log.d("TOKEN", uri.toString())

            val code = uri.getQueryParameter("code")
            val type = "authorization_code"
            Log.d("CODE", code)
            val builder = Retrofit.Builder()
                    .baseUrl("https://oauth.yandex.ru/")
                    .addConverterFactory(GsonConverterFactory.create())

            val retrofit = builder.build()
            val client = retrofit.create(LoginService::class.java)
            val call = client.getAccessToken(clientId, clientSecret, code, type)

            call.enqueue(object: Callback<AccessToken>{
                override fun onFailure(call: Call<AccessToken>?, t: Throwable?) {
                    Log.d("test", "failure")
                }

                override fun onResponse(call: Call<AccessToken>?, response: Response<AccessToken>?) {
                    Log.d("test", "success")
                    val res1 = response?.body()?.getAccessToken() ?: "fail"
                    Log.d("token", res1)
                }
            })

        }
    }

    private fun getAccessToken() {
        val clientId = "0d0970774e284fa8ba9ff70b6b06479a"
        val clientSecret = "a7ee508b75664955bd5d5ad59f66b196"
        val callback = "https://oauth.yandex.ru/verification_code"

        val uri = "https://oauth.yandex.ru/authorize?response_type=code&client_id=$clientId&redirect_uri=$callback"
        Log.d("uri", uri)
        val codeIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse(uri))
        startActivity(codeIntent)
    }


    private fun onLogin() : Boolean {
        getAccessToken()
        return true
    }

    private fun getAcc() : Boolean {
        val am = AccountManager.get(this)

        val yandexAccounts = am.getAccountsByType("com.yandex.passport")
        var acc : Account? = null
        for (value in yandexAccounts) {
            if (value.name.startsWith("ser")) {
                acc = value
                break
            }
        }
        val clientId = "0d0970774e284fa8ba9ff70b6b06479a"
        val clientSecret = "a7ee508b75664955bd5d5ad59f66b196"
        if (acc != null) {
            Log.d("name", acc.name)

            val options = Bundle()
            options.putString("clientId", clientId)
            options.putString("clientSecret", clientSecret)
            try {
                am.getAuthToken(acc, clientId, options, true,
                        { bundle ->
                            try {
                                val token = bundle.result.getString(AccountManager.KEY_AUTHTOKEN)
                                Log.d("token", token)
                            } catch (ex: Exception) {
                                Log.e("ex", ex.message)
                            }
                        }, null)

            } catch (ex: Exception) {
                Log.e("exception", ex.message)
            }

//            val task = @SuppressLint("StaticFieldLeak")
//            object :  AsyncTask<Void, Boolean, Boolean>() {
//                override fun doInBackground(vararg p0: Void?): Boolean {
//                    try {
//                        val options = Bundle()
//                        options.putString("clientId", clientId)
//                        options.putString("clientSecret", clientSecret)
//                        val authToken = am.getAuthToken(acc, clientId, options, true, null, null)
//                        val token = authToken.result.getString(AccountManager.KEY_AUTHTOKEN)
//                        Log.d("token", token)
//                    } catch (ex: Exception) {
//                        Log.e("exception", ex.message)
//                    }
//                    return true
//                }
//            }
//            task.execute()

        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> getAcc()
            R.id.action_login -> onLogin()
            else -> super.onOptionsItemSelected(item)
        }
    }
}
