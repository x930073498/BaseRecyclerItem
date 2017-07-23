package com.mvvm.x930073498.library

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by x930073498 on 2017/7/22.
 */
fun Any?.logD() {
    Log.d("XJ", toString())
}

fun Any?.logD(target: Any?) {
    target.logD()
}

var mToast: Toast? = null

@SuppressLint("ShowToast")
fun Context.toast(msg: CharSequence?, duration: Int) {
    if (mToast == null) {
        mToast = Toast.makeText(this.applicationContext, msg, duration)
    } else {
        mToast!!.setText(msg)
        mToast!!.duration = duration
    }
    mToast?.show()
}

fun Context.toast(msg: CharSequence?) {
    toast(msg, Toast.LENGTH_SHORT)
}