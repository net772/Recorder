package com.example.recorder

import com.example.recorder.Permission.PermissionConstant
import com.example.recorder.Permission.PermissionListener

interface BaseNavigator {
    fun requestPermission(permissionConstant: PermissionConstant, permissionListener: PermissionListener?)
   // fun callExternalContent(uri: Uri, requestCode: Int)
}