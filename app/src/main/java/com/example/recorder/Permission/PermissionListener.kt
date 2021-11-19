package com.example.recorder.Permission

interface PermissionListener {
    fun onPermissionGranted()
    fun onPermissionDenied()
}