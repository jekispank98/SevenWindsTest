package com.example.sevenwindstest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.domain.model.RegisterModel
import com.example.sevenwindstest.MainViewModel
import com.example.sevenwindstest.databinding.FragmentSignInBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: MainViewModel by sharedViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btLogin.setOnClickListener {
            if (checkValidData()) {
                CoroutineScope(Dispatchers.Default).launch {
                    val isLoginSuccess =
                        viewModel.getUserAuth(
                            RegisterModel(
                                binding.edLogin.text.toString(),
                                binding.edEnterPassword.text.toString()
                            )
                        )
                    navigateToCoffeeShopsFragment(isLoginSuccess)
                }
            }
        }
    }

    private fun navigateToCoffeeShopsFragment(isLoginSuccess: Boolean) {
        if (isLoginSuccess) {
            requireActivity().runOnUiThread {
                val action = SignInFragmentDirections.actionSignInFragmentToCoffeShopsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun checkValidData(): Boolean {
        return !(binding.edLogin.text?.isBlank() == true
                || binding.edEnterPassword.text?.isBlank() == true)
    }

    companion object {

        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}