package com.example.mvvm.extensions

import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.example.mvvm.R


fun AppCompatImageView.loadUrl(url: String?) {
    if (url != null) {
        load(url) {
            placeholder(R.drawable.bg_no_image)
        }
    } else {
        load(R.drawable.bg_no_image)
    }
}





