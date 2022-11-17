package com.looktabinc.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.looktabinc.R
import com.looktabinc.databinding.LayoutPlaceBinding

class PlaceSelectView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var binding: LayoutPlaceBinding? = null
    val map = mapOf<Int, String>(
        1 to resources.getString(R.string.cafe),
        2 to resources.getString(R.string.restaurant),
        3 to resources.getString(R.string.bar),
        4 to resources.getString(R.string.shopping),
        5 to resources.getString(R.string.cultural_life),
        6 to resources.getString(R.string.fitness),
    )
    private val selectList = mutableSetOf<Int>()

    interface OnItemClickListener {
        fun onItemClick(select: List<String>)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    init {
        initView()
    }

    private fun initView() {
        binding = LayoutPlaceBinding.inflate(LayoutInflater.from(context), this, false)
        setBasicStyle()
        clickListener()
        addView(binding!!.root)
    }


    fun setValue(view: View, int: Int) {
        if (int in selectList) {
            setStyleOff(view as ConstraintLayout)
            selectList.remove(int)
            mListener?.onItemClick(map.filter { it.key in selectList }.map { it.value })
        } else if (selectList.size <= 2) {
            setStyleOn(view as ConstraintLayout)
            selectList.add(int)
            mListener?.onItemClick(map.filter { it.key in selectList }.map { it.value })
        }
    }

    private fun clickListener() {
        binding?.layoutItemCafe?.setOnClickListener {
            setValue(it, 1)
        }
        binding?.layoutItemRestaurant?.setOnClickListener {
            setValue(it, 2)
        }
        binding?.layoutItemBar?.setOnClickListener {
            setValue(it, 3)
        }
        binding?.layoutItemShopping?.setOnClickListener {
            setValue(it, 4)
        }
        binding?.layoutItemCulturalLife?.setOnClickListener {
            setValue(it, 5)
        }
        binding?.layoutItemFitness?.setOnClickListener {
            setValue(it, 6)
        }
    }

    private fun setBasicStyle() {
        binding?.layoutJoin?.let { parent ->
            val count: Int = parent.childCount
            for (i in 0..count) {
                if (parent.getChildAt(i) is LinearLayoutCompat) {
                    val layout3 = (parent.getChildAt(i) as LinearLayoutCompat)
                    val layout3Count = layout3.childCount
                    for (count in 0..layout3Count) {
                        if (layout3.getChildAt(count) is ConstraintLayout) {
                            val constraintLayout = layout3.getChildAt(count)
                            setStyleOff(constraintLayout as ConstraintLayout)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setStyleOn(layout: ConstraintLayout) {
        val count: Int = layout.childCount
        for (i in 0..count) {
            when (val view = layout.getChildAt(i)) {
                is LinearLayoutCompat -> {
                    view.background = resources.getDrawable(R.drawable.box_focused)
                }
                is AppCompatImageView -> {
                    view.setImageDrawable(resources.getDrawable(R.drawable.ic_checkbox_on))
                }
            }
        }
    }

    private fun setStyleOff(layout: ConstraintLayout) {
        val count: Int = layout.childCount
        for (i in 0..count) {
            when (val view = layout.getChildAt(i)) {
                is LinearLayoutCompat -> {
                    view.background = null
                }
                is AppCompatImageView -> {
                    view.setImageDrawable(resources.getDrawable(R.drawable.ic_checkbox_off))
                }
            }
        }
    }

}