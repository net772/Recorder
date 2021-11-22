package com.example.recorder.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.recorder.permissiondialog.PermissionDialog
import com.example.recorder.permissiondialog.PermissionDialogNavigator
import com.example.recorder.R

object PermissionUtil {
    private const val REQUEST_RECORD_AUDIO_PERMISSION = 201

    private var mPermissionConstant : PermissionConstant? = null // 체크 및 요청하고자 하는 권한
    private var mPermissionListener: PermissionListener? = null // 권한 획득 또는 거절 시 발생하는 이벤트 리스너

    fun easyPermission(activity: Activity, permissionConstant: PermissionConstant, permissionListener: PermissionListener?, isShowDialog : Boolean = true) {
        mPermissionListener = permissionListener
        mPermissionConstant = permissionConstant

        val permissions = permissionConstant.getPermissions()
        // 한번이라도 거부했으면, 먼저 다이어로그로 띄우고 묻는다.
        if(shouldShowRequestPermissionRationale(activity, permissions)) {
            if(isShowDialog) {
                // 다이얼로그 띄우는 처리
                val permissionsMsg = permissionConstant.getPermissionsMsgMap()
                val permissionTitle = permissionsMsg[KEY_TITLE]!!
                val permissionMsg = permissionsMsg[KEY_MSG]!!

                PermissionDialog(activity, 0, permissionTitle, permissionMsg,
                    R.string.common_cancel, R.string.common_confirm, object :
                        PermissionDialogNavigator {
                        override fun onClickLeftButton(id: Int) { onPermissionDenied() }
                        override fun onClickRightButton(id: Int) {
                            ActivityCompat.requestPermissions(activity, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
                        }
                    }).show()
            } else{
                ActivityCompat.requestPermissions(activity, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
            }
        } else {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkPermission(activity, permissions))
                    onPermissionGranted()
                else {
                    ActivityCompat.requestPermissions(activity, permissions, REQUEST_RECORD_AUDIO_PERMISSION) //요청권한을 배열로 담아서 권한 요청
                }
            }
        }


    private fun onPermissionGranted() {
        mPermissionListener?.onPermissionGranted()
        mPermissionListener = null
    }

    private fun onPermissionDenied() {
        mPermissionListener?.onPermissionDenied()
        mPermissionListener = null
    }

    fun checkPermission(context: Context, permissions: Array<String>) : Boolean {
        var hasPermission = true
        permissions.forEach { permission ->
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false
                return@forEach
            }
        }
        return hasPermission
    }

    private fun shouldShowRequestPermissionRationale(activity: Activity, permissions: Array<String>) : Boolean {
        var hasPermission = false

        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(activity,
                    permission) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = true
                return@forEach
            }
        }
        return hasPermission
    }
}