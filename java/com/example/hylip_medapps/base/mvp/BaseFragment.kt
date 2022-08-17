package com.windranger.reminder.base.mvp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import com.windranger.reminder.R


/**
 * Created by didik on 27/11/17.
 */
abstract class BaseFragment : Fragment() {
    //protected lateinit var loadingBar: TruvelDialog
    protected lateinit var mContext: Context
    //private val dialog by lazy { IOSDialog(mContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true);
        mContext = activity!!
        /*loadingBar = TruvelDialog(mContext);
        loadingBar.setCancelable(false);
*/
        /*dialog.apply {
            setPositiveLabel("Dismiss")
            setPositiveListener { dialog.dismiss() }
        }*/
    }

    protected fun showLoadingBar() {
        showLoadingBar(getString(R.string.loading))
    }

    protected fun showLoadingBar(message: String) {
        /*loadingBar.setTitle(message)
        loadingBar.show()*/
    }

    protected fun hideLoadingBar() {
        //loadingBar.dismiss()
    }

    protected fun showPopup(message: String) {
        /*dialog.setSubtitle(message)
        dialog.show()*/
    }

    protected fun terminateRefreshing(mSwpieRefresh: SwipeRefreshLayout?) {
        mSwpieRefresh?.isRefreshing = false
        mSwpieRefresh?.destroyDrawingCache()
        mSwpieRefresh?.clearAnimation()
    }
}