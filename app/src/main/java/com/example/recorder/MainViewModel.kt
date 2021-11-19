package com.example.recorder

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.recorder.Permission.PermissionConstant
import com.example.recorder.Permission.PermissionListener

class MainViewModel : ViewModel() {
    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }

    private lateinit var mContext: Context
    private lateinit var mNavigator: BaseNavigator
    private lateinit var mPermissionCk: PermissionCheck
    val state = ObservableField<State>(State.BEFORE_RECORDING)
    val enable = ObservableField<Boolean>(false)

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private lateinit var recordingFilePath: String

    fun init(applicationContext: Context, navigator: BaseNavigator, permissionCheck: PermissionCheck) {
        mContext = applicationContext
        mNavigator = navigator
        mPermissionCk = permissionCheck



        recordingFilePath = "${mContext.externalCacheDir?.absolutePath}/recording.3gp"

    }

    private fun requestAudioPermission() {
        mNavigator.requestPermission(
            PermissionConstant.AUDIO_PERMISSIONS,
            object : PermissionListener {
                override fun onPermissionGranted() {
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

    private fun resetButton() {
        state.set(State.BEFORE_RECORDING)
        setEnable()
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
                Log.d("동현","false")
                enable.set(false)
            }
        }
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recordingFilePath)
            prepare()
        }

        recorder?.start()
        state.set(State.ON_RECORDING)
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        state.set(State.AFTER_RECORDING)
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            setDataSource(recordingFilePath)
            prepare()
        }
        player?.start()
        state.set(State.ON_PLAYING)
    }

    private fun stopPlaying() {
        player?.release()
        player = null
        state.set(State.AFTER_RECORDING)
    }

}