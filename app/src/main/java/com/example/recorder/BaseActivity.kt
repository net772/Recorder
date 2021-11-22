package com.example.recorder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.recorder.permission.PermissionConstant
import com.example.recorder.permission.PermissionListener
import com.example.recorder.permission.PermissionUtil

abstract class BaseActivity<DB: ViewDataBinding> : AppCompatActivity(), BaseNavigator  {
    lateinit var mBinding: DB

    abstract fun getLayoutResourceId(): Int
    abstract fun initDataBinding()
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())

        initDataBinding()
        initView()
    }

    /** Permission */
    override fun requestPermission(permissionConstant: PermissionConstant, permissionListener: PermissionListener?) {
        PermissionUtil.easyPermission(this, permissionConstant, permissionListener)
    }
}