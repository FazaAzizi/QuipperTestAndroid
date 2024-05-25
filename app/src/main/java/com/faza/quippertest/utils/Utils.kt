package com.faza.quippertest.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.WindowCompat
import androidx.viewpager2.widget.ViewPager2
import com.faza.quippertest.R
import com.faza.quippertest.common.HtmlImageGetter
import com.bumptech.glide.Glide
import com.chuckerteam.chucker.BuildConfig
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {

    fun String.formatRupiah(): String {
        return this.reversed().chunked(3).joinToString(".").reversed()
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.visible(isVisible: Boolean = true) {
        if (isVisible) this.visibility = View.VISIBLE
        else this.visibility = View.GONE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.gone(isGone: Boolean = true) {
        if (isGone) this.visibility = View.GONE
        else this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.setOnSingleClickListener(action: () -> Unit) {
        this.setOnClickListener { view ->
            synchronized(view) {
                view.isEnabled = false
                view.postDelayed({ view.isEnabled = true }, 500L)
                action()
            }
        }
    }

    fun Context.toastLong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Context.toastShort(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun ViewPager2.next() {
        when (currentItem) {
            childCount -> if (childCount >= currentItem)
                setCurrentItem(currentItem + 1, true)

            else -> setCurrentItem(currentItem + 1, true)
        }
    }

    fun ViewPager2.previous() {
        when (currentItem) {
            childCount -> if (childCount > 0)
                setCurrentItem(childCount - 1, true)

            else -> setCurrentItem(currentItem - 1, true)
        }
    }

    fun Context.copyToClipboard(data: String, message: String = "Copy to Clipboard") {
        val clipboard = getSystemService((Context.CLIPBOARD_SERVICE)) as ClipboardManager
        val clip = ClipData.newPlainText("main-clipboard", data)
        clipboard.setPrimaryClip(clip)
        this.toastShort(message)
    }

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
        }

    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

    fun Context.simpleShareText(body: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(intent, "Share Using"))
    }

    fun String?.isValidEmail(): Boolean {
        return !TextUtils.isEmpty(this) && !this.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(
            this
        ).matches()
    }

    fun String.isValidPhoneNumber(): Boolean {
        return (this.startsWith("08") || this.startsWith("628") || this.startsWith("+628") || this.startsWith(
            "+62"
        ))
    }

    fun String.isValidPhoneLength(): Boolean {
        return this.length >= 11
    }

    fun String.cleanPhoneNumber(): String {
        if (this.startsWith("62")) return this.replaceFirst("62", "0")
        if (this.startsWith("+62")) return this.replaceFirst("+62", "0")
        return this
    }

    fun String.capitalizeEachWord(): String {
        return this.split(" ").joinToString(" ") { it.replaceFirstChar(Char::titlecase) }
    }

    fun appLog(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.i(tag, message)
    }

    fun getLanguageCode(): String {
        return when (Locale.getDefault().language) {
            "in" -> "IN"
            else -> "EN"
        }
    }

    fun Activity?.setStatusBarColor(@ColorRes colorRes: Int, isLightColor: Boolean) = this?.let {
        val window = it.window
        window.statusBarColor = ContextCompat.getColor(it, colorRes)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            isLightColor
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    fun String.dateTimeFormat(
        sourceFormat: String = "dd-MM-yyyy hh:mm:ss",
        targetFormat: String = "dd MMM yyyy"
    ): String {
        val date = SimpleDateFormat(sourceFormat, Locale.getDefault()).parse(this)
            ?: return ""
        return SimpleDateFormat(targetFormat, Locale.getDefault()).format(date)
    }

    fun TextView.setHtmlView(html: String?) {
        val imageGetter = HtmlImageGetter(resources, this)

        val styledText= html?.let {
            HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_MODE_LEGACY,
                imageGetter,null)
        }

        this.movementMethod = LinkMovementMethod.getInstance()
        this.text = styledText?.trim()
    }

    fun ImageView.setImageViewUrl(url: String?){
        Glide.with(this.rootView).load(url).error(R.drawable.sample_promo_1).into(this)
    }
}