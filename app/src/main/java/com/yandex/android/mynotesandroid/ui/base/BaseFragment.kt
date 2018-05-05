package com.yandex.android.mynotesandroid.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseFragment<TPresenter : BasePresenter<TView>, TView : BaseView> : Fragment(), BaseView
{
    protected open var mPresenter: TPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter = createPresenter()
    }

    protected open fun createPresenter(): TPresenter? {
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onViewDestroyed()
    }
}