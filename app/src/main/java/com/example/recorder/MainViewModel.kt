package com.example.recorder

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.recorder.customview.SoundVisualizerView
import com.example.recorder.permission.PermissionConstant
import com.example.recorder.permission.PermissionListener

class MainViewModel : ViewModel() {

    private lateinit var mContext: Context
    private lateinit var mNavigator: BaseNavigator
    val state = ObservableField<State>()
    val enable = ObservableField<Boolean>(false)
    val show = ObservableField<Boolean>(false)
    val replay = ObservableField<Boolean>(false)
    val countState = ObservableField<Boolean>(false)
    val reset = ObservableField<Boolean>(false)
    val countView = ObservableField<String>("00:00")

    val recorder = ObservableField<MediaRecorder?>()

    private var player: MediaPlayer? = null
    private lateinit var recordingFilePath: String


    fun init(applicationContext: Context, navigator: BaseNavigator) {
        mContext = applicationContext
        mNavigator = navigator

        recordingFilePath = "${mContext.externalCacheDir?.absolutePath}/recording.3gp"

        state.set(State.BEFORE_RECORDING)
    }

    private fun requestAudioPermission() {
        mNavigator.requestPermission(
            PermissionConstant.AUDIO_PERMISSIONS,
            object : PermissionListener {
                override fun onPermissionGranted() {
                    Log.d("동현","onPermissionGranted")

                    recordButton()
                }

                override fun onPermissionDenied() {
                    Log.d("동현","onPermissionDenied")

                }

            }
        )
    }


    val onClickListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.recordButton -> requestAudioPermission()
            R.id.resetButton -> resetButton()
        }
    }

    private fun changeState(showState: Boolean?, replayState : Boolean?) {
        show.set(showState)

        replayState?.let {
            replay.set(replayState)
        }
    }

    private fun resetButton() {
        setEnable()
        stopPlaying()
        reset.set(true)

        state.set(State.BEFORE_RECORDING)
    }

    private fun recordButton() {
        Log.d("동현","recordButton : ${state.get()}")
        setEnable()
        when(state.get()) {
            State.BEFORE_RECORDING -> startRecording()
            State.ON_RECORDING -> stopRecording()
            State.AFTER_RECORDING -> startPlaying()
            State.ON_PLAYING -> stopPlaying()
        }
    }

    private fun setEnable() {
        when(state.get()) {
            State.AFTER_RECORDING, State.ON_PLAYING -> enable.set(true)
            else -> {
                enable.set(false)
            }
        }
    }

    private fun startRecording() {
        Log.d("동현","startRecording")
        recorder.set(MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recordingFilePath)
            prepare()
        })

        recorder.get()?.start()
        changeState(true, false)
        countState.set(true)
        state.set(State.ON_RECORDING)
    }

    private fun stopRecording() {
        Log.d("동현","stopRecording")
        recorder.get()?.stop()
        recorder.set(null)
        changeState(false, null)
        countState.set(false)
        state.set(State.AFTER_RECORDING)
        recorder.get()?.release()
    }

    private fun startPlaying() {
        Log.d("동현","startPlaying")
        player = MediaPlayer().apply {
            setDataSource(recordingFilePath)
            prepare()
        }

        player?.setOnCompletionListener {
            Log.d("동현","setOnCompletionListener")
            stopPlaying()
            state.set(State.AFTER_RECORDING)
        }

        player?.start()
        changeState(true, true)

        countState.set(true)
        state.set(State.ON_PLAYING)
    }

    private fun stopPlaying() {
        Log.d("동현","stopPlaying")
        player?.release()
        player = null
        changeState(false, null)
        countState.set(false)
        state.set(State.AFTER_RECORDING)
    }

}