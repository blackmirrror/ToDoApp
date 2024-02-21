package ru.blackmirrror.todoapp.presentation.auth

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
import ru.blackmirrror.todoapp.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private val viewModel by viewModel<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNextBtn()
        observer()
    }

    private fun setNextBtn() {
        binding.btnAuthNext.setOnClickListener {
            viewModel.register(
                binding.etAuthEmail.text.toString(),
                binding.etAuthEmail.text.toString()
            )
        }
    }

    private fun observer(){
        viewModel.isRegister.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    findNavController().navigate(R.id.action_authFragment_to_todoItemsFragment)
                }
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}