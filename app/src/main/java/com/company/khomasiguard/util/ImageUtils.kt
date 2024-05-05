package com.company.khomasiguard.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64


fun String.convertToBitmap(): Bitmap? {
    val byte = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byte, 0, byte.size)
}