package com.mvvm.x930073498.library

import android.database.DataSetObservable
import android.database.DataSetObserver
import android.databinding.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.SpinnerAdapter

/**
 * Created by x930073498 on 2017/7/22.
 */
@Suppress("UNCHECKED_CAST")
open class BaseAdapter : RecyclerView.Adapter<BaseHolder>(), ListAdapter, SpinnerAdapter {
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
    private var listViewTypeMap = mutableMapOf<Int, Int>()
    private var callBack = DefaultOnRebindCallback()

    override fun isEmpty(): Boolean {
        return data.isEmpty()
    }

    var isFromList = false
    val mDataObservable: DataSetObservable by lazy {
        DataSetObservable()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: BaseHolder? = convertView?.tag as? BaseHolder
        val layoutId = data[position].getLayoutId()
        if (holder == null) {
            holder = createListViewHolder(parent, layoutId)
            holder.itemView.tag = holder
        }
        holder.item = data[position]
        onBindViewHolder(holder, position)
        return holder.itemView!!
    }

    override fun registerDataSetObserver(observer: DataSetObserver) {
        mDataObservable.registerObserver(observer)
    }

    fun notifyListDataSetChange() {
        mDataObservable.notifyChanged()
    }

    fun notifyDataSetInvalidated() {
        mDataObservable.notifyInvalidated()
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getViewTypeCount(): Int {
        var count = 0
        listViewTypeMap.clear()
        data.iterator().forEach {
            if (!listViewTypeMap.containsKey(it.getLayoutId())) {
                listViewTypeMap.put(it.getLayoutId(), count)
                count++
            }
        }
        return count
    }

    override fun isEnabled(position: Int): Boolean {
        return true
    }

    override fun areAllItemsEnabled(): Boolean {
        isFromList = true
        return true
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        mDataObservable.unregisterObserver(observer)
    }

    override fun getCount(): Int {
        return itemCount
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    internal inner class BaseOnListChangedCallback : ObservableList.OnListChangedCallback<ObservableArrayList<BaseItem>>() {
        override fun onItemRangeRemoved(p0: ObservableArrayList<BaseItem>?, p1: Int, p2: Int) {
            notifyItemRangeRemoved(p1, p2)
            mDataObservable.notifyChanged()
        }

        override fun onChanged(p0: ObservableArrayList<BaseItem>?) {
            notifyDataSetChanged()
            mDataObservable.notifyChanged()
        }

        override fun onItemRangeChanged(p0: ObservableArrayList<BaseItem>?, p1: Int, p2: Int) {
            notifyItemChanged(p1, p2)
            mDataObservable.notifyChanged()
        }

        override fun onItemRangeInserted(p0: ObservableArrayList<BaseItem>?, p1: Int, p2: Int) {
            notifyItemRangeInserted(p1, p2)
            mDataObservable.notifyChanged()
        }

        override fun onItemRangeMoved(p0: ObservableArrayList<BaseItem>?, p1: Int, p2: Int, p3: Int) {
            notifyItemMoved(p1, p2)
            mDataObservable.notifyChanged()
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    final override fun getItemViewType(position: Int): Int {
        if (isFromList) return getListViewType(position) else return getRecyclerViewType(position)
    }

    fun getListViewType(position: Int): Int {
        val layoutId = data[position].getLayoutId()
        return listViewTypeMap[layoutId]!!
    }

    fun getRecyclerViewType(position: Int): Int {
        val layout = data[position].getLayoutId()
        return layout
    }

    override fun onBindViewHolder(holder: BaseHolder?, position: Int) {
        val item: BaseItem? = getItem(position) as BaseItem
        if (holder?.item == null) {
            bindViewHolder(holder, item, position)
        } else {
            bindViewHolder(holder, holder.item, position)
        }
    }

    private fun bindViewHolder(holder: BaseHolder?, item: BaseItem?, position: Int) {
        holder!!.dataBinding!!.removeOnRebindCallback(callBack)
        if (item?.getVariableId() == BaseItem.NO_ID) {
            holder.dataBinding!!.addOnRebindCallback(callBack)
        } else {
            holder.dataBinding!!.setVariable(item?.getVariableId()!!, item)
            holder.dataBinding.executePendingBindings()
        }
        item.onBindView(holder.dataBinding, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    final override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseHolder {
        val view = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        return BaseHolder(view)
    }

    fun createListViewHolder(parent: ViewGroup?, layoutId: Int): BaseHolder {
        val view = LayoutInflater.from(parent?.context).inflate(layoutId, parent, false)
        return BaseHolder(view)
    }

    inner class DefaultOnRebindCallback : OnRebindCallback<ViewDataBinding>() {
        override fun onPreBind(binding: ViewDataBinding): Boolean {
            return false
        }
    }
}


class BaseHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var dataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    internal var item: BaseItem? = null
}


