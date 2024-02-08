package com.mertyigit0.habittrackereasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
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
        habitList = ArrayList()
        habitAdapter = HabitAdapter(habitList)

        loadHabitsFromDatabase()
    }

    private fun loadHabitsFromDatabase() {
        val databaseHelper = DatabaseHelper(requireContext())
        val habitsWithData = databaseHelper.getAllHabitsWithData()

        habitList.clear()
        habitList.addAll(habitsWithData)

        habitAdapter.notifyDataSetChanged()
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
        val activityContext = requireActivity()
        val builder = AlertDialog.Builder(activityContext)
        builder.setTitle("Add New Habit")

        val inputLayout = LinearLayout(activityContext)
        inputLayout.orientation = LinearLayout.VERTICAL

        val nameEditText = EditText(activityContext)
        nameEditText.hint = "Name"
        inputLayout.addView(nameEditText)

        val descriptionEditText = EditText(activityContext)
        descriptionEditText.hint = "Description"
        inputLayout.addView(descriptionEditText)

        builder.setView(inputLayout)

        builder.setPositiveButton("Add") { dialog, _ ->
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (name.isNotEmpty()) {
                val newHabit = Habit(id = -1L, name = name, description = description)
                val databaseHelper = DatabaseHelper(activityContext)
                val habitId = databaseHelper.addHabit(newHabit) // Veritabanına alışkanlık ekle

                if (habitId != -1L) {
                    newHabit.id = habitId // Alışkanlığın ID'sini ayarla
                    habitList.add(newHabit) // Yeni alışkanlığı listeye ekle
                    habitAdapter.notifyItemInserted(habitList.size - 1) // RecyclerView'i güncelle
                } else {
                    Toast.makeText(activityContext, "Failed to add habit to database", Toast.LENGTH_SHORT).show()
                }
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


