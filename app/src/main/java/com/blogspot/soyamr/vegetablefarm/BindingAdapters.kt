package com.blogspot.soyamr.vegetablefarm

import androidx.databinding.BindingAdapter
import com.google.android.material.slider.Slider


@BindingAdapter(value = ["onValueChangeListener"])
fun setOnValueChangeListener(slider: Slider, listener: OnValueChangeListener) {
    slider.addOnChangeListener { _: Slider?, value: Float, _: Boolean ->
        listener.onValueChanged(value)
    }
}


interface OnValueChangeListener {
    fun onValueChanged(value: Float)
}
