package com.company.khomasiguard.util


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import android.content.Context


fun String.toDateTime(format: String = "yyyy-MM-dd'T'HH:mm:ss"): Date? {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return try {
        sdf.parse(this)
    } catch (ex: Exception) {
        null
    }
}

fun Context.navigateToCall(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    startActivity(this, intent, null)
}

