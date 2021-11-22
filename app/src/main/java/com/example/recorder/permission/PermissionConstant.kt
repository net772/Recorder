package com.example.recorder.permission

import android.Manifest

const val KEY_TITLE = "title" // 권한 다이어로그 제목
const val KEY_MSG = "msg" // 권한 다이어로그 내용

enum class PermissionConstant(_permissions: Array<String>, _permissionMap: HashMap<String, Int>) {
    // 오디오기능 요청권한 사용 명시
    AUDIO_PERMISSIONS(arrayOf(Manifest.permission.RECORD_AUDIO),
    HashMap<String, Int>().apply {
        put(KEY_TITLE,  com.example.recorder.R.string.permission_storage_title)
        put(KEY_MSG, com.example.recorder.R.string.permission_storage_radio)
    }),

    EXTERNAL_STORAGE_PERMISSIONS(
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),
        HashMap<String, Int>().apply{
            put(KEY_TITLE,  com.example.recorder.R.string.permission_storage_title)
            put(KEY_MSG, com.example.recorder.R.string.permission_storage_msg)
        }
    );

    private val permissions = _permissions
    private val permissionsMsgMap = _permissionMap
    fun getPermissions() = permissions
    fun getPermissionsMsgMap() = permissionsMsgMap
}