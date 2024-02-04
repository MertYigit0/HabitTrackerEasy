package com.mertyigit0.habittrackereasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertyigit0.habittrackereasy.databinding.FragmentHabitListBinding


class HabitListFragment : Fragment() {

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private lateinit var habitList: ArrayList<Habit>
    private lateinit var habitAdapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        habitList = ArrayList() // habitList'i başlat
        habitAdapter = HabitAdapter(habitList) // HabitAdapter'ı başlat
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext()) // LinearLayoutManager'ı ayarla
            adapter = habitAdapter // HabitAdapter'ı ayarla
        }

        binding.addButton.setOnClickListener {
            fabClicked()
        }
    }

    private fun fabClicked() {
        val activityContext = requireActivity() // Aktivitenin Context'ini al
        val builder = AlertDialog.Builder(activityContext)
        builder.setTitle("Add New Habit")

        val inputLayout = LinearLayout(activityContext)
        inputLayout.orientation = LinearLayout.VERTICAL

        val nameEditText = EditText(activityContext)
        nameEditText.hint = "Name"
        inputLayout.addView(nameEditText)

        val descriptionEditText = EditText(activityContext)
        descriptionEditText.hint = "Count"
        inputLayout.addView(descriptionEditText)

        builder.setView(inputLayout)

        builder.setPositiveButton("Add") { dialog, _ ->
            val name = nameEditText.text.toString()
            val count = descriptionEditText.text.toString()

            if (name.isNotEmpty()) {
                val newHabit = Habit(name, description = count)
                habitList.add(newHabit)
                habitAdapter.notifyDataSetChanged() // RecyclerView'i güncelle
            }

            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


