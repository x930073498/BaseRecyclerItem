package com.mvvm.x930073498.baserecycleritem

import android.content.Intent
import android.databinding.ViewDataBinding
import android.view.View
import com.mvvm.x930073498.baserecycleritem.databinding.LauoutItemTestItemBinding
import com.mvvm.x930073498.library.*

/**
 * Created by x930073498 on 2017/7/22.
 */
class TestItem : AbstractBaseItem() {

    var name: CharSequence? = null

    var title: CharSequence? = null


    override fun getLayoutId(): Int {
        return R.layout.lauout_item_test_item
    }

    override fun onBindView(binding: ViewDataBinding?, position: Int) {
        if (binding is LauoutItemTestItemBinding) {
            binding.tvName.text = name
            binding.tvTitle.text = title
            binding.button.setOnClickListener { test(it) }
        }
    }

    fun test(v: View) {
        sendLocalBroadcast(v.context, Intent("delete").putExtra("data", this))
    }

}



