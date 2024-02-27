package ru.blackmirrror.todoapp.presentation.todoItems

import android.os.Bundle
import android.util.Log
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
    private lateinit var itemsAdapter: TodoItemsAdapter
    private var visibleDone = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()
        observe()
    }

    private fun initFields() {
        setButtons()
        initRecycler()
    }

    private fun setButtons() {
        binding.floatingButton.setOnClickListener {
            val action = TodoItemsFragmentDirections.actionTodoItemsFragmentToEditFragment(null)
            findNavController().navigate(action)
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
        binding.ivVisible.setOnClickListener {
            viewModel.changeVisibilityCompleted()
        }
    }

    private fun initRecycler() {
        itemsAdapter = TodoItemsAdapter()
        binding.rvTodoItems.adapter = itemsAdapter
        itemsAdapter.onTodoItemLongClickListener = {
            viewModel.changeCompleted(it.id, !it.completed)
        }
        itemsAdapter.onTodoItemClickListener = {
            val action = TodoItemsFragmentDirections.actionTodoItemsFragmentToEditFragment(it.id)
            findNavController().navigate(action)
        }
    }

    private fun observe() {
        viewModel.uid.observe(viewLifecycleOwner) {
            if (it == null)
                findNavController().navigate(R.id.action_todoItemsFragment_to_loginFragment)
            else
                viewModel.loadTasks()
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            itemsAdapter.submitList(it ?: arrayListOf())
        }

        viewModel.countCompletedTasks.observe(viewLifecycleOwner) {
            binding.tvCount.text = "Выполнено - $it"
        }

        viewModel.isVisibleCompleted.observe(viewLifecycleOwner) {
            if (it) {
                binding.ivVisible.setImageResource(R.drawable.ic_visibility_off)
            } else {
                binding.ivVisible.setImageResource(R.drawable.ic_visibility_on)
            }
        }
    }
}