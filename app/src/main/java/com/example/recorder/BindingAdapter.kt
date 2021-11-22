package com.example.recorder

import android.media.MediaRecorder
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import com.example.recorder.customview.SoundVisualizerView

object BindingAdapter {

    @BindingAdapter("onClickListener")
    @JvmStatic
    fun setOnclickListener(view: View, listener: View.OnClickListener?) {
        view.setOnClickListener(listener)
    }

    @BindingAdapter("setDrawable")
    @JvmStatic
    fun RecordButton.setDrawable(state: State) {
        updateIconWithState(state)
    }

    @BindingAdapter("setAmplitude")
    @JvmStatic
    fun SoundVisualizerView.setAmplitude(recoder : MediaRecorder?) {
            onRequestCurrentAmplitude = {
                recoder?.maxAmplitude ?: 0
            }
    }

    @BindingAdapter(value = ["showVisible", "replayCk"], requireAll = false)
    @JvmStatic
    fun SoundVisualizerView.showVisible(state: Boolean, replayState: Boolean) {
        if(state) startVisualizing(replayState) else {
            stopVisualizing()
        }
    }

    @BindingAdapter("resetView")
    @JvmStatic
    fun SoundVisualizerView.resetView(resetState: Boolean) {
        if(resetState) clearVisualization()
    }

    @BindingAdapter("resetCount")
    @JvmStatic
    fun CountUpView.resetCount(resetState: Boolean) {
        if(resetState) clearCountTime()
    }


    @BindingAdapter("countUp")
    @JvmStatic
    fun CountUpView.countUp(countState: Boolean) {
        if(countState) startCountUp() else stopCountUp()
    }

}