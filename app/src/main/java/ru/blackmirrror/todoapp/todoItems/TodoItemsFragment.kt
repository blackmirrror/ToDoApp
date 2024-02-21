package ru.blackmirrror.todoapp.todoItems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.blackmirrror.todoapp.R
import ru.blackmirrror.todoapp.databinding.FragmentTodoItemsBinding

class TodoItemsFragment : Fragment() {

    private lateinit var binding: FragmentTodoItemsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
    }

    private fun setButtons() {
        binding.floatingButton.setOnClickListener {
            val action = TodoItemsFragmentDirections.actionTodoItemsFragmentToEditFragment()
            findNavController().navigate(action)
        }
        binding.btnLogout.setOnClickListener {
            val action = TodoItemsFragmentDirections.actionTodoItemsFragmentToAuthFragment()
            findNavController().navigate(action)
        }
    }
}