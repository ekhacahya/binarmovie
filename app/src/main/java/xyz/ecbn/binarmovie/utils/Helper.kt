package xyz.ecbn.binarmovie.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */

fun View.setPaddingRight(value: Int) = setPadding(paddingLeft, paddingTop, value, paddingBottom)
fun View.setPaddingLeft(value: Int) = setPadding(value, paddingTop, paddingRight, paddingBottom)
fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.showSnackbar(msg: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, timeLength).show()
}

fun View.show(isShow: Boolean) {
    if (isShow) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Number.prettyCount(): String? {
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val numValue: Long = this.toLong()
    val value = floor(log10(numValue.toDouble())).toInt()
    val base = value / 3
    return if (value >= 3 && base < suffix.size) {
        DecimalFormat("#0.0").format(
            numValue / Math.pow(
                10.0,
                base * 3.toDouble()
            )
        ) + suffix[base]
    } else {
        DecimalFormat("#,##0").format(numValue)
    }
}

fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }