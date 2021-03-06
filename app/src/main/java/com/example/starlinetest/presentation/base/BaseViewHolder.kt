package com.example.starlinetest.presentation.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<out T>(view: View) : RecyclerView.ViewHolder(view) where T : ViewDataBinding {
    val binding: T? = DataBindingUtil.bind(view)
}