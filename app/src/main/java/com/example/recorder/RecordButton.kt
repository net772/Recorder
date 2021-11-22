package com.example.recorder

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton

class RecordButton(context: Context, attrs: AttributeSet) : AppCompatImageButton(context, attrs) {

    init {
        setBackgroundResource(R.drawable.shape_oval_button)
    }
    fun updateIconWithState(state: State) {
        when(state) {
            State.BEFORE_RECORDING ->{
                //녹음 전
                setImageResource(R.drawable.ic_record)
            }
            State.ON_RECORDING -> {
                // 녹음 중
                setImageResource(R.drawable.ic_stop)
            }
            State.AFTER_RECORDING -> {
                // 녹음 완료
                setImageResource(R.drawable.ic_play)
            }

            State.ON_PLAYING -> {
                // 재생생
                setImageResource(R.drawable.ic_stop)
           }
        }
    }

}