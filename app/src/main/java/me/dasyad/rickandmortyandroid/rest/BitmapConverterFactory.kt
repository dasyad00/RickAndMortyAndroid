package me.dasyad.rickandmortyandroid.rest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class BitmapConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, Bitmap>? {
        return if (type == Bitmap::class.java) {
            Converter<ResponseBody, Bitmap> {
                BitmapFactory.decodeStream(it.byteStream())
            }
        } else {
            null
        }
    }
}