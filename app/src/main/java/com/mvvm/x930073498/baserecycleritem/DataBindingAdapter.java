package com.mvvm.x930073498.baserecycleritem;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

/**
 * Created by x930073498 on 2017/7/22.
 */

public class DataBindingAdapter {
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);

    }
    @BindingAdapter("itemAnimator")
    public static void setItemAnimator(RecyclerView view,RecyclerView.ItemAnimator animator){
        view.setItemAnimator(animator);
    }

}
