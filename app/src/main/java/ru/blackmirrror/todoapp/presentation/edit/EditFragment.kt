package ru.blackmirrror.todoapp.presentation.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.blackmirrror.todoapp.R
import ru.blackmirrror.todoapp.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbarEdit.title = ""
        binding.toolbarEdit.setNavigationIcon(R.drawable.baseline_close_24)
        binding.toolbarEdit.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}