package com.marcelomotta.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ButtonFragment : Fragment() {
    private var listener: OnConfirmClickListener? = null

    interface OnConfirmClickListener {
        fun onConfirmClicked(activityType: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_button, container, false)

        val confirmButton = view.findViewById<Button>(R.id.btn_confirm)
        confirmButton.setOnClickListener {
            listener?.onConfirmClicked("activity1")
        }

        return view
    }

    fun setOnConfirmClickListener(listener: OnConfirmClickListener) {
        this.listener = listener
    }
}