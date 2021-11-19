package com.example.recorder

import android.util.Log
import androidx.activity.viewModels
import com.example.recorder.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(), PermissionCheck {
    override fun getLayoutResourceId() = R.layout.activity_main
    private val viewModel : MainViewModel by viewModels()

    override fun initDataBinding() {
        mBinding.vm = viewModel
        viewModel.init(applicationContext, this, this)
    }

    override fun initView() = Unit
    override fun permissionCheck(ck: Boolean) {
        Log.d("동현","permissionCheck : $ck")
        if(!ck) {
            finish()
        }
    }
}

interface PermissionCheck{
    fun permissionCheck(ck : Boolean)
}