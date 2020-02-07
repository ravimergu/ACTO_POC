package com.example.acto_poc.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.acto_poc.di.GlideApp


@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) {

    if(url.startsWith("http")) {
        val glideUrl = GlideUrl(
           url, LazyHeaders.Builder()
               .addHeader(
                   "User-Agent",
                   "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36"
               )
               .build()
       )
        GlideApp.with(context)
            .asBitmap()
            .load(glideUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }else{
        GlideApp.with(context)
            .asBitmap()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }

}