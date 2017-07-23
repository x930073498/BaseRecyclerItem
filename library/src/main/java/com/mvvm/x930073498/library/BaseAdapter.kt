package com.mvvm.x930073498.library

import android.databinding.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by x930073498 on 2017/7/22.
 */
@Suppress("UNCHECKED_CAST")
open class BaseAdapter : RecyclerView.Adapter<BaseHolder>() {
    private inner class BaseOnListChangedCallback : ObservableList.OnListChangedCallback<ObservableArrayList<BaseItem>>() {
        override fun onItemRangeRemoved(p0: ObservableArrayList<BaseItem>?, p1: Int, p2: Int) {
            notifyItemRangeRemoved(p1, p2)
        }

        override fun onChanged(p0: ObservableArrayList<BaseItem>?) {
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(p0: ObservableArrayList<BaseItem>?, p1: Int, p2: Int) {
            notifyItemChanged(p1, p2)
        }

        override fun onItemRangeInserted(p0: ObservableArrayList<BaseItem>?, p1: Int, p2: Int) {
            notifyItemRangeInserted(p1, p2)
        }

        override fun onItemRangeMoved(p0: ObservableArrayList<BaseItem>?, p1: Int, p2: Int, p3: Int) {
            notifyItemMoved(p1, p2)
        }

    }

    var dataObservable = true
        set(value) {
            field = value

            if (!value) {
                if (data is ObservableArrayList) (data as ObservableArrayList<BaseItem>).removeOnListChangedCallback(callback)
                return
            }
            if (data is ObservableArrayList) {
                (data as ObservableArrayList<BaseItem>).removeOnListChangedCallback(callback)
                (data as ObservableArrayList<BaseItem>).addOnListChangedCallback(callback)
            } else {
                val temp = ObservableArrayList<BaseItem>()
                temp.addAll(data)
                temp.addOnListChangedCallback(callback)
                data = temp
            }
        }
    private val callback: BaseOnListChangedCallback by lazy {
        BaseOnListChangedCallback()
    }
    var data = listOf<BaseItem>()
        set(value) {
            if (dataObservable) {
                val temp = ObservableArrayList<BaseItem>()
                temp.addAll(value)
                temp.addOnListChangedCallback(callback)
                field = temp
            } else field = value
        }
    private var map = mutableMapOf<Int, Int>()
    private var callBack = DefaultOnRebindCallback<ViewDataBinding>()

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        val type = (if (false) -1 else data[position].getType())
        val layout = (if (false) -1 else data[position].getLayoutId())
        if (type == -1 || layout == -1) return 0
        if (map.containsKey(type)) return type
        map.put(type, layout)
        return type
    }

    private fun getLayout(type: Int): Int? {
        return map[type]
    }

    override fun onBindViewHolder(holder: BaseHolder?, position: Int) {
        val item: BaseItem? = with(data) {
            get(position)
        }
        holder!!.dataBinding!!.removeOnRebindCallback(callBack)
        if (item?.getVariableId() == BaseItem.NO_ID) {
            holder.dataBinding!!.addOnRebindCallback(callBack)
        } else {
            holder.dataBinding!!.setVariable(item?.getVariableId()!!, item)
            holder.dataBinding.executePendingBindings()
        }
        bindHolder(item, holder, position)
    }

    fun bindHolder(item: BaseItem?, holder: BaseHolder?, position: Int) {
        item?.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseHolder {
        val layoutId = getLayout(viewType)
        val view = LayoutInflater.from(parent?.context).inflate(layoutId!!, parent, false)
        return BaseHolder(view)
    }

    inner class DefaultOnRebindCallback<E : ViewDataBinding?> : OnRebindCallback<E>() {
        override fun onPreBind(binding: E): Boolean {
            return false
        }
    }
}

inline fun <reified T : ViewDataBinding> BaseHolder.getBinding(): T? {
    if (getInternalBinding() is T) return getInternalBinding() as T
    return null
}

class BaseHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var dataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    fun getInternalBinding(): ViewDataBinding {
        return dataBinding
    }
}


