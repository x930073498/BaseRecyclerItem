package com.mvvm.x930073498.baserecycleritem

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.mvvm.x930073498.baserecycleritem.databinding.ActivityMainBinding
import com.mvvm.x930073498.library.BaseAdapter
import com.mvvm.x930073498.library.BaseItem
import com.mvvm.x930073498.library.registerLocalReceiver
import com.mvvm.x930073498.library.unregisterLocalReceiver
class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    val adapter: BaseAdapter by lazy {
        BaseAdapter().apply {
            data = createData()
        }
    }
    val deleteReceiver: BroadcastReceiver by lazy {
        DeleteBroadcastReceiver()
    }
    val itemAnimator: DefaultItemAnimator by lazy {
        DefaultItemAnimator()
    }
    val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.adapter = adapter
        binding.itemAnimator = itemAnimator
        binding.layoutManger = layoutManager
        registerLocalReceiver()
    }

    private fun registerLocalReceiver() {
        registerLocalReceiver(deleteReceiver, IntentFilter("delete"))
    }

    private fun createData(): MutableList<BaseItem> {
        val data = arrayListOf<BaseItem>()
        for (i in 1..40) {
            if (i > 20) data.add(createTest(i)) else data.add(createTest1(i))
        }
        return data
    }

    inner class DeleteBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val item = p1?.getSerializableExtra("data") as? BaseItem
            if (adapter.data is MutableList) {
                (adapter.data as MutableList<BaseItem>).remove(item)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterLocalReceiver(deleteReceiver)
    }

    private fun createTest(i: Int): TestItem {
        val item = TestItem()
        item.name = "名字".plus(i)
        item.title = "标题".plus(i)
        return item
    }

    private fun createTest1(i: Int): TestItem1 {
        val item = TestItem1()
        item.name = "名字".plus(i)
        item.title = "标题".plus(i)
        return item
    }


}



