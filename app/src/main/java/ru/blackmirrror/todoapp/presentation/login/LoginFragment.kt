package ru.blackmirrror.todoapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.blackmirrror.todoapp.R
import ru.blackmirrror.todoapp.data.utils.UiState
import ru.blackmirrror.todoapp.databinding.FragmentLoginBinding
import ru.blackmirrror.todoapp.presentation.auth.AuthViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
        observer()
    }

    private fun setButtons() {
        binding.btnLoginNext.setOnClickListener {
            viewModel.login(
                binding.etLoginEmail.text.toString(),
                binding.etLoginPassword.text.toString()
            )
        }

        binding.tvLoginAuth.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_authFragment)
        }
    }

    private fun observer(){
        viewModel.isLogin.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_todoItemsFragment)
                }
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}