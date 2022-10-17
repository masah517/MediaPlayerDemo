package com.example.mediaplayerdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.mediaplayerdemo.databinding.DcPlayerViewBinding

class DcPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

    val binding : DcPlayerViewBinding = DcPlayerViewBinding.inflate(LayoutInflater.from(context), this, true)
}
