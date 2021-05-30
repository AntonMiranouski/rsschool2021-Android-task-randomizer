package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    interface OnFirstFragmentDataListener {
        fun onFirstFragmentDataListener(min: Int, max: Int)
    }

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var listener: OnFirstFragmentDataListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnFirstFragmentDataListener?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val min = view.findViewById<EditText>(R.id.min_value)
        val max = view.findViewById<EditText>(R.id.max_value)

        generateButton?.setOnClickListener {
            try {
                val minValue = min.text.toString().toInt()
                val maxValue = max.text.toString().toInt()

                if (minValue > maxValue || maxValue > Int.MAX_VALUE) {
                    throw IllegalArgumentException()
                }
                listener?.onFirstFragmentDataListener(minValue, maxValue)
            } catch (e: IllegalArgumentException) {
                val toast =
                    Toast.makeText(activity, "Please enter correct data.", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}