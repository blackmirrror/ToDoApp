package ru.blackmirrror.todoapp.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.blackmirrror.todoapp.R
import ru.blackmirrror.todoapp.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
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
    }

    private fun setNextBtn() {
        binding.btnAuthNext.setOnClickListener {
            val action = AuthFragmentDirections.actionAuthFragmentToTodoItemsFragment()
            findNavController().navigate(action)
        }
    }
}