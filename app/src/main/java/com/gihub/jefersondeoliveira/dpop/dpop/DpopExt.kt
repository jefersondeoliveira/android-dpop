package com.gihub.jefersondeoliveira.dpop.dpop

import android.util.Base64
import java.math.BigInteger

fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)

fun BigInteger.toUnsignedByteArray(): ByteArray {
    val byteArray = this.toByteArray()
    return if (byteArray[0] == 0.toByte()) byteArray.copyOfRange(1, byteArray.size) else byteArray
}
