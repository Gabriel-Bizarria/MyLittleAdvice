package com.littleadvice.mylittleadvice.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.littleadvice.mylittleadvice.ui.helpers.dateFormatter
import com.littleadvice.mylittleadvice.databinding.FragmentDailyAdviceBinding
import com.littleadvice.mylittleadvice.ui.viewmodel.MainViewModel
import java.util.*

const val MAIN_PHRASE = "MAIN_PHRASE"
const val DAY_SAVED = "DAY_SAVED"
const val SHARED_PREFS = "com.littleadvice.mylittleadvice.sharedprefs"

class DailyAdviceFragment : Fragment() {

    private var _binding: FragmentDailyAdviceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }
    val actualDayDate by lazy { dateFormatter.format(Date()) }

    private var date: String? = null
    private var text: String? = null

    private val sharedPreferencesAdvice: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            SHARED_PREFS, MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyAdviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        sharedPreferencesAdvice.getString(MAIN_PHRASE, "").let { text = it }
        sharedPreferencesAdvice.getString(DAY_SAVED, actualDayDate).let { date = it }

        Log.v("SHARED_PREFS", "$text")

        if (text.isNullOrBlank() || date != actualDayDate) {
            resetSharedPref()
            viewModel.getAdvice()
            viewModel.adviceLiveData.observe(viewLifecycleOwner) {

                text = it
                date = actualDayDate

                binding.mainPhrase.text = it
                binding.progressBar.visibility = View.GONE
            }
        } else {
            binding.mainPhrase.text = text
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        sharedPreferencesAdvice.let {
            val editor = sharedPreferencesAdvice.edit()
            editor.putString(MAIN_PHRASE, text)
            editor.putString(DAY_SAVED, date)
            editor.apply()
        }
        Log.v("SHARED_PREFS", "${sharedPreferencesAdvice.all}")
    }

    fun resetSharedPref() {
        sharedPreferencesAdvice.let {
            val editor = it.edit()
            editor.clear()
            editor.apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}