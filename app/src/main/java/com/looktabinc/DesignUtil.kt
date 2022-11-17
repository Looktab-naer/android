package com.looktabinc

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DesignUtil {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImageUrl(url: String?) {
        url?.let {
            Glide.with(context)
                .load(it)
                .thumbnail(0.1f)
                .placeholder(ColorDrawable(Color.parseColor("#DEE2E6")))
                .into(this)
        }
    }


    @JvmStatic
    @BindingAdapter("imageCircleUrl")
    fun ImageView.setImageCircleUrl(url: String?) {
        url?.let {
            Glide.with(context)
                .load(it)
                .circleCrop()
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun View.setVisibleGone(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun View.setVisibleInvisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}