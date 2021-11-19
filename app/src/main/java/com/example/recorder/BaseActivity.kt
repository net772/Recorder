package com.example.recorder

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.recorder.Permission.PermissionConstant
import com.example.recorder.Permission.PermissionListener
import com.example.recorder.Permission.PermissionUtil

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