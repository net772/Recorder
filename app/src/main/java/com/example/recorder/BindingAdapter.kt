package com.example.recorder

import android.media.MediaRecorder
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import com.example.recorder.customView.SoundVisualizerView

object BindingAdapter {

    @BindingAdapter("onClickListener")
    @JvmStatic
    fun setOnclickListener(view: View, listener: View.OnClickListener?) {
        view.setOnClickListener(listener)
    }

    @BindingAdapter("setDrawable")
    @JvmStatic
    fun RecordButton.setDrawable(state: State) {
        Log.d("동현","state:$state")
        updateIconWithState(state)
    }

    @BindingAdapter("setAmplitude")
    @JvmStatic
    fun SoundVisualizerView.setAmplitude(recoder : MediaRecorder?) {
        onRequestCurrentAmplitude = {
            recoder?.maxAmplitude ?: 0
        }
    }
}