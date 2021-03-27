package com.example.githuber

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.auth_bottom_sheet.*


class AuthBottomSheet(private val login: Boolean, private var submitListener: SubmitListener?): BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(
            DialogFragment.STYLE_NORMAL,
            R.style.AppBottomSheetDialogTheme
        )
        return super.onCreateDialog(savedInstanceState)
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.auth_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (login){
            tv_username.visibility = GONE
            tv_username_header.visibility = GONE
            tv_info.text = "Please log in to continue"
        }
        else{
            tv_info.text = "Please sign up to continue"
        }

        submit.setOnClickListener {
            if(login){
                if(!tv_pw.text.isNullOrEmpty())
                    submitListener?.submitName(null, tv_pw.text.toString())
            }
            else{
                if(!tv_username.text.isNullOrEmpty() && !tv_pw.text.isNullOrEmpty())
                    submitListener?.submitName(tv_username.text.toString(), tv_pw.text.toString())
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        submitListener = null
    }

    interface SubmitListener {
        fun submitName(name: String?, password: String)
    }
}