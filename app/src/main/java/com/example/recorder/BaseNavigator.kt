package com.example.recorder

import com.example.recorder.permission.PermissionConstant
import com.example.recorder.permission.PermissionListener

interface BaseNavigator {
    fun requestPermission(permissionConstant: PermissionConstant, permissionListener: PermissionListener?)
}