package ru.blackmirrror.todoapp.presentation.todoItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.blackmirrror.todoapp.R
import ru.blackmirrror.todoapp.databinding.FragmentTodoItemsBinding

class TodoItemsFragment : Fragment() {

    private lateinit var binding: FragmentTodoItemsBinding
    private val viewModel by viewModel<TodoItemsViewModel>()
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
        observe()
    }

    private fun setButtons() {
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_todoItemsFragment_to_editFragment)
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observe() {
        viewModel.userEmail.observe(viewLifecycleOwner) {
            if (it == null)
                findNavController().navigate(R.id.action_todoItemsFragment_to_loginFragment)
        }
    }
}