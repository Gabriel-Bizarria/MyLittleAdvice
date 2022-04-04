package com.littleadvice.mylittleadvice.ui.fragments

import android.content.res.Resources
import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.littleadvice.mylittleadvice.R
import com.littleadvice.mylittleadvice.databinding.FragmentRandomBinding
import com.littleadvice.mylittleadvice.ui.viewmodel.MainViewModel


class RandomFragment : Fragment() {

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRandomBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.getAdvice()

        viewModel.adviceLiveData.observe(viewLifecycleOwner){
            binding.progressBar.visibility = View.GONE
            binding.mainPhrase.text = it
        }
    }

    override fun onResume() {
        super.onResume()

        binding.randomBt.setOnClickListener {
            binding.mainPhrase.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.randomBt.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_gray_1))
            binding.randomBt.text = resources.getString(R.string.wait_a_second)

            viewModel.getAdvice()
        }

        viewModel.adviceLiveData.observe(viewLifecycleOwner){
            binding.mainPhrase.text = it
            binding.progressBar.visibility = View.GONE
            binding.mainPhrase.visibility = View.VISIBLE

            binding.randomBt.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main_purple))
            binding.randomBt.text = resources.getString(R.string.random_bt)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}