package com.example.recorder.permissiondialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.recorder.R
import com.example.recorder.databinding.DialogPermissionBinding

class PermissionDialog(private val _context: Context, _dialogId: Int, _title: Int, _message :Int, _leftBtnStrResId: Int, _rightBtnStrResId: Int, _navigator: PermissionDialogNavigator?) : Dialog(_context) {
    private val dialogId = _dialogId
    val title = _title
    val leftBtnText = _leftBtnStrResId
    val rightBtnText = _rightBtnStrResId
    private val message = _message
    private val navigator = _navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("동현","PermissionDialog")
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<DialogPermissionBinding>(inflater, R.layout.dialog_permission, null, false)

        binding.dialog = this
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)

        val targetString = "설정 > 앱 > 클럽라이브 > 권한설정"
        val msg = _context.getString(message)

        if(rightBtnText == R.string.setting) {
            binding.tvText.text = setSpanText(msg,targetString)
        } else {
            binding.tvText.text = msg
        }
    }

    private var isClickBtn = false

    val onClickListener = View.OnClickListener { view ->
        if (!isClickBtn) {
            isClickBtn = true

            when (view.id) {
                R.id.dialog_left_btn -> navigator?.onClickLeftButton(dialogId)
                R.id.dialog_right_btn -> navigator?.onClickRightButton(dialogId)
            }
            dismiss()
        }
    }

    private fun setSpanText(string: String, targetString: String): SpannableString {
        val spannableString = SpannableString(string)
        val targetStartIndex = string.indexOf(targetString)
        val targetEndIndex = targetStartIndex + targetString.length
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#000000")), targetStartIndex, targetEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannableString.setSpan(StyleSpan(Typeface.BOLD), targetStartIndex, targetEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
}
