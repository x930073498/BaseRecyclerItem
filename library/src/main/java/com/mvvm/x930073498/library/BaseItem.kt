package com.mvvm.x930073498.library

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.ViewDataBinding
import android.support.v4.content.LocalBroadcastManager
import java.io.Serializable

/**
 * Created by x930073498 on 2017/7/22.
 */
interface BaseItem : Serializable {
    companion object {
        val NO_ID = -1
    }

    /**
     * 返回布局id
     */
    fun getLayoutId(): Int

    /**
     * 返回Databinding绑定的数据variableId
     */
    fun getVariableId(): Int

    /**
     * 绑定数据，注意此处显示的是当前实体类的数据，不用去实体类列表中取出实体再去赋值，多此一举
     */
    fun onBindView(binding: ViewDataBinding?, position: Int)
}

fun Any.sendLocalBroadcast(context: Context, intent: Intent) {
    LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
}

fun Context.sendLocalBroadcast(intent: Intent) {
    sendLocalBroadcast(this, intent)
}

fun Any.unregisterLocalReceiver(context: Context, broadcastReceiver: BroadcastReceiver) {
    LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver)
}

fun Any.registerLocalReceiver(context: Context, broadcastReceiver: BroadcastReceiver, intentFilter: IntentFilter) {
    LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter)
}

fun Context.unregisterLocalReceiver(broadcastReceiver: BroadcastReceiver) {
    unregisterLocalReceiver(this, broadcastReceiver)
}

fun Context.registerLocalReceiver(broadcastReceiver: BroadcastReceiver, intentFilter: IntentFilter) {
    registerLocalReceiver(this, broadcastReceiver, intentFilter)
}