package ru.blackmirrror.todoapp.presentation.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.blackmirrror.todoapp.R
import ru.blackmirrror.todoapp.data.Importance
import ru.blackmirrror.todoapp.data.Task
import ru.blackmirrror.todoapp.data.utils.TextFormatter.fromLongToString
import ru.blackmirrror.todoapp.databinding.FragmentEditBinding
import java.util.Calendar
import java.util.Date

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val viewModel by viewModel<EditViewModel>()
    private val args: EditFragmentArgs by navArgs()

    private var currentTask: Task = Task()

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
        initFields()
        observer()
        initChangeTask()
    }

    private fun initToolbar() {
        binding.toolbarEdit.title = ""
        binding.toolbarEdit.setNavigationIcon(R.drawable.baseline_close_24)
        binding.toolbarEdit.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initFields() {
        val id: String? = args.taskId
        if (id != null && id != "") {
            viewModel.getTask(id)
            binding.editDeleteBtn.isEnabled = true
            binding.editDeleteBtn.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.color_red)
            )
            binding.ivDelete.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color_red))
        }
    }

    private fun observer() {
        viewModel.task.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                currentTask = task
                with(binding) {
                    editText.setText(task.text)
                    setImportance(task.importance)
                    task.deadlineDate?.let { setDeadline(it) }
                }
            }
        }
    }

    private fun initChangeTask() {
        binding.editChangeImportance.setOnClickListener {
            showPopUpMenu()
        }
        binding.editSwitchDeadline.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                showDatePickerDialog()
        }

        binding.editSaveBtn.setOnClickListener {
            currentTask.text = binding.editText.text.toString()
            viewModel.saveTask(currentTask)
            findNavController().popBackStack()
        }
        binding.editDeleteBtn.setOnClickListener {
            currentTask.text = binding.editText.text.toString()
            viewModel.deleteTask(currentTask.id)
            findNavController().popBackStack()
        }
    }

    private fun showPopUpMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.editChangeImportance)
        popupMenu.inflate(R.menu.importance)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val importance = when (menuItem.itemId) {
                R.id.action_default -> Importance.NONE
                R.id.action_lower -> Importance.LOW
                R.id.action_higher -> Importance.HIGH
                else -> Importance.NONE
            }
            setImportance(importance)
            true
        }
        popupMenu.show()
    }

    private fun setImportance(importance: Importance) {
        when (importance) {
            Importance.NONE -> binding.editImportance.text = "Нет"
            Importance.LOW -> binding.editImportance.text = "Низкий"
            Importance.HIGH -> binding.editImportance.text = "!!Высокий"
        }
        currentTask.importance = importance
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val dateTimePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        calendar.set(year, month, dayOfMonth, hourOfDay, minute)
                        setDeadline(calendar.timeInMillis)
                    },
                    currentHourOfDay,
                    currentMinute,
                    true
                )

                timePickerDialog.show()
                timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.color_blue))
                timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.color_blue))
            },
            currentYear,
            currentMonth,
            currentDayOfMonth
        )

        dateTimePickerDialog.show()
        dateTimePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.color_blue))
        dateTimePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.color_blue))
    }

    private fun setDeadline(deadline: Long) {
        currentTask.deadlineDate = deadline
        binding.editDeadline.text = fromLongToString(deadline)
    }
}