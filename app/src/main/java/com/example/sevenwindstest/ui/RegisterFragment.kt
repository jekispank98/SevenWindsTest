package com.example.sevenwindstest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.domain.model.RegisterModel
import com.example.sevenwindstest.R
import com.example.sevenwindstest.MainViewModel
import com.example.sevenwindstest.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btRegistration.setOnClickListener {
             if (checkValidData() && checkPasswordMatching()) {
                 CoroutineScope(Dispatchers.Default + CoroutineExceptionHandler{_, throwable ->
                     throwable.printStackTrace()
                 }).launch {
                     val isRegSuccess =
                     viewModel.getRegistration(
                         RegisterModel(
                             binding.edLogin.text.toString(),
                             binding.edPassword.text.toString()
                         )
                     )
                     navigateToLoginFragment(isRegSuccess)
                 }
             }
            else if (!checkValidData()) {
                Toast.makeText(requireContext(), getString(R.string.ENTER_VALID_DATA), Toast.LENGTH_SHORT).show()
             }
            else if (!checkPasswordMatching()) {
                 Toast.makeText(requireContext(), getString(R.string.PASSWORDS_ARE_NOT_MATCH), Toast.LENGTH_SHORT).show()
             }
        }
    }

    private fun navigateToLoginFragment(isRegSuccess: Boolean) {
        if (isRegSuccess) {
            requireActivity().runOnUiThread {
                val action = RegisterFragmentDirections.actionRegisterFragmentToSignInFragment()
                this.findNavController().navigate(action)
            }
        }
    }

    private fun checkPasswordMatching(): Boolean {
        return binding.edPassword.text.toString() == binding.edRepeatPassword.text.toString()
    }

    private fun checkValidData(): Boolean {
        return !(binding.edLogin.text?.isBlank() == true ||
                binding.edPassword.text?.isBlank() == true ||
                binding.edRepeatPassword.text?.isBlank() == true)
    }

    companion object {

        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}