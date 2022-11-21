package com.looktabinc

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    @BindingAdapter("imageUrlVisible")
    fun ImageView.setImageUrlVisible(url: String?) {
      if (url==null){
          visibility =  View.GONE
      }
      else{
          View.VISIBLE
          url?.let {
              Glide.with(context)
                  .load(it)
                  .thumbnail(0.1f)
                  .placeholder(ColorDrawable(Color.parseColor("#DEE2E6")))
                  .into(this)
          }
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
    @BindingAdapter("statusText")
    fun TextView.setStatusText(int: Int?) {
        when (int) {
            1 -> {
                text = "Relaxed"
                setTextColor(this.resources.getColor(R.color.green))
                val img = this.resources.getDrawable(R.drawable.ic_ellipse)
                img.setBounds(0, 0, 20, 20)
                setCompoundDrawables(img, null, null, null)
            }
            2 -> {
                text = "Normal"
                setTextColor(this.resources.getColor(R.color.blue))
                val img = this.resources.getDrawable(R.drawable.ic_ellipse_blue)
                img.setBounds(0, 0, 20, 20)
                setCompoundDrawables(img, null, null, null)
            }
            3 -> {
                text = "Crowded"
                setTextColor(this.resources.getColor(R.color.red))
                val img = this.resources.getDrawable(R.drawable.ic_ellipse_red)
                img.setBounds(0, 0, 20, 20)
                setCompoundDrawables(img, null, null, null)
            }
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