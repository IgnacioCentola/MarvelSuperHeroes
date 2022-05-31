package com.nacho.marvelsuperheroes.feature_list.presentation.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.nacho.marvelsuperheroes.R

private fun loadImage(
    imageView: ImageView,
    url: String,
    circularProgressDrawable: CircularProgressDrawable
) {
    val requestOptions = RequestOptions()
        .placeholder(circularProgressDrawable)
        .error(R.mipmap.ic_launcher)
        .transform(RoundedCorners(15))
    Glide.with(imageView)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

private fun getProgressDrawable(context: Context): CircularProgressDrawable {
    val cpd = CircularProgressDrawable(context)
    cpd.apply {
        strokeWidth = 10f
        centerRadius = 50f
        setColorSchemeColors(
            R.color.purple_500
        )
    }
    cpd.start()
    return cpd
}

@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    loadImage(imageView, url, getProgressDrawable(imageView.context))
}