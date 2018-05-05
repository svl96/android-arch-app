package com.yandex.android.mynotesandroid.ui.base


abstract class BasePresenter<TView : BaseView>{

    protected var mView : TView? = null

    open fun <E : TView> attachView(view: E) {
        mView = view
        onViewAttached(view)
    }

    protected open fun onViewAttached(view: TView) {}

    fun detachView() {
        mView = null
    }

    fun onViewDestroyed() {
        detachView()
    }

}