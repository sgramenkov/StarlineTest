package com.example.starlinetest.presentation.modules.cars.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.starlinetest.R
import com.example.starlinetest.databinding.ItemCarBinding
import com.example.starlinetest.databinding.ItemCarCategoryBinding
import com.example.starlinetest.domain.entity.Car
import com.example.starlinetest.presentation.base.BaseViewHolder
import java.lang.IllegalArgumentException

class CarsAdapter(private val list: List<Car>?, private val listener: CarListener) :
    RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>>() {

    companion object {
        private const val STATE_CAR = 1
        private const val STATE_ROOT = 0
    }


    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        val item = list?.get(position)

        if (holder.binding is ItemCarBinding) {

            holder.binding.item = item
            holder.binding.listener = listener

        } else if (holder.binding is ItemCarCategoryBinding) {

            holder.binding.item = item

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            STATE_CAR -> BaseViewHolder(
                DataBindingUtil.inflate<ItemCarBinding>(
                    inflater,
                    R.layout.item_car,
                    parent,
                    false
                ).root
            )

            STATE_ROOT -> BaseViewHolder(
                DataBindingUtil.inflate<ItemCarCategoryBinding>(
                    inflater,
                    R.layout.item_car_category,
                    parent,
                    false
                ).root
            )

            else -> throw IllegalArgumentException("No such view type")
        }

    }

    private fun isRootItem(item: Car?): Boolean = item?.parent == "root"

    override fun getItemViewType(position: Int): Int {
        val item = list?.get(position)

        return if (isRootItem(item))
            STATE_ROOT
        else
            STATE_CAR
    }

    override fun getItemCount(): Int = list?.size ?: 0

    interface CarListener {
        fun onCarItemClick(item: Car?)
    }
}