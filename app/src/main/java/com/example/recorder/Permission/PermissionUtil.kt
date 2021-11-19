package com.example.recorder.Permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtil {

    private var mPermissionConstant : PermissionConstant? = null // 체크 및 요청하고자 하는 권한
    private var mPermissionListener: PermissionListener? = null // 권한 획득 또는 거절 시 발생하는 이벤트 리스너

    fun easyPermission(activity: Activity, permissionConstant: PermissionConstant, permissionListener: PermissionListener?, isShowDialog : Boolean = false) {
        mPermissionListener = permissionListener
        mPermissionConstant = permissionConstant

        val permissions = permissionConstant.getPermissions()
        Log.d("동현","easyPermission")
        // 한번이라도 거부했으면, 먼저 다이어로그로 띄우고 묻는다.
        if(shouldShowRequestPermissionRationale(activity, permissions)) {
            Log.d("동현","shouldShowRequestPermissionRationale")
            if(isShowDialog) {
                // 다이얼로그 띄우는 처리
            } else{
                ActivityCompat.requestPermissions(activity, permissions, 1000)
            }
        } else {
            Log.d("동현","else")
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkPermission(activity, permissions))
                    onPermissionGranted()
                else {
                    Log.d("동현","권한요청")
                    ActivityCompat.requestPermissions(activity, permissions, 1000) //요청권한을 배열로 담아서 권한 요청
                }
            }
        }


    private fun onPermissionGranted() {
        mPermissionListener?.onPermissionGranted()
        mPermissionListener = null
    }

    private fun onPermissionDenied() {
        mPermissionListener?.onPermissionDenied()
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