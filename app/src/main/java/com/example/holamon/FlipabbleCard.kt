package com.example.holamon
import android.graphics.drawable.Drawable
import android.widget.ImageView

class FlippableCard(val imageView: ImageView) {
    var isFlipped: Boolean = false
    var idImage: Int = R.drawable.hearth_cardback
    var locked: Boolean = false
}
