package com.example.recorder

import androidx.activity.viewModels
import com.example.recorder.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutResourceId() = R.layout.activity_main
    private val viewModel : MainViewModel by viewModels()

    override fun initDataBinding() {
        mBinding.vm = viewModel
        viewModel.init(applicationContext, this)
    }

    override fun initView() = Unit
}