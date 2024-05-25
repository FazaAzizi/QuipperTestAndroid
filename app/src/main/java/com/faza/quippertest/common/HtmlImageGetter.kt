package com.faza.quippertest.common

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HtmlImageGetter(
    private val res: Resources,
    private val htmlTextView: TextView
) : Html.ImageGetter {

    override fun getDrawable(url: String): Drawable {
        val holder = BitmapDrawablePlaceHolder(res, null)
  
        GlobalScope.launch(Dispatchers.IO) {
            runCatching {
  
                val bitmap = Glide.with(htmlTextView.context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()
                val drawable = BitmapDrawable(res, bitmap)
  
                // To make sure Images don't go out of screen , Setting width less
                // than screen width, You can change image size if you want
                val width = getScreenWidth() - 150
  
                // Images may stretch out if you will only resize width,
                // hence resize height to according to aspect ratio
                val aspectRatio: Float =
                    (drawable.intrinsicWidth.toFloat()) / (drawable.intrinsicHeight.toFloat())
                val height = width / aspectRatio
                drawable.setBounds(10, 20, width, height.toInt())
                holder.setDrawable(drawable)
                holder.setBounds(10, 20, width, height.toInt())
                withContext(Dispatchers.Main) {
                    htmlTextView.text = htmlTextView.text
                }
            }
        }
        return holder
    }
  
    // Actually Putting images
    internal class BitmapDrawablePlaceHolder(res: Resources, bitmap: Bitmap?) :
        BitmapDrawable(res, bitmap) {
        private var drawable: Drawable? = null
  
        override fun draw(canvas: Canvas) {
            drawable?.run { draw(canvas) }
        }
  
        fun setDrawable(drawable: Drawable) {
            this.drawable = drawable
        }
    }
  
    // Function to get screenWidth used above
    fun getScreenWidth() =
        Resources.getSystem().displayMetrics.widthPixels
}