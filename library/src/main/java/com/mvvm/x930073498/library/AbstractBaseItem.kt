package com.mvvm.x930073498.library

/**
 * Created by x930073498 on 2017/7/22.
 */
abstract class AbstractBaseItem : BaseItem {
    override fun getType(): Int {
        return getLayoutId()
    }

    override fun getVariableId(): Int {
        return BaseItem.NO_ID
    }

    override fun onBindViewHolder(holder: BaseHolder?, position: Int) {

    }
}