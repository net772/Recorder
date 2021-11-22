package com.example.recorder.permission

interface PermissionListener {
    fun onPermissionGranted()
    fun onPermissionDenied()
}