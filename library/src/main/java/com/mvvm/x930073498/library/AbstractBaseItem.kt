package com.mvvm.x930073498.library

import android.databinding.ViewDataBinding

/**
 * Created by x930073498 on 2017/7/22.
 */
abstract class AbstractBaseItem : BaseItem {

    override fun getVariableId(): Int {
        return BaseItem.NO_ID
    }

    override fun onBindView(binding: ViewDataBinding?, position: Int) {
    }
}