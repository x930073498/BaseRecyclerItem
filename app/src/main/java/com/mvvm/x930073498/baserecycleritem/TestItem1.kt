package com.mvvm.x930073498.baserecycleritem

import android.content.Intent
import android.view.View
import com.mvvm.x930073498.baserecycleritem.databinding.LauoutItemTestItem1Binding
import com.mvvm.x930073498.library.*

/**
 * Created by x930073498 on 2017/7/22.
 */
class TestItem1 : AbstractBaseItem() {
    var name: CharSequence? = null
    var title: CharSequence? = null
    override fun getVariableId(): Int {
        return BR.data
    }

    override fun getLayoutId(): Int {
        return R.layout.lauout_item_test_item_1
    }

    fun test(v: View) {
        sendLocalBroadcast(v.context, Intent("delete").putExtra("data", this))
    }
}