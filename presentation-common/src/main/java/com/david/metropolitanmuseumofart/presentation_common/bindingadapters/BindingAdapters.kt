package com.david.metropolitanmuseumofart.presentation_common.bindingadapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("imageUrl", "placeHolder", requireAll = false)
fun ImageView.loadImage(
    imageUrl: String?,
    @DrawableRes placeholder: Int? = null,
) {
    val requestBuilder = Glide.with(context).load(imageUrl)
    placeholder?.let {
        requestBuilder.placeholder(it)
    }
    requestBuilder.listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean,
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean,
        ): Boolean {
            return false
        }
    }).into(this)
}