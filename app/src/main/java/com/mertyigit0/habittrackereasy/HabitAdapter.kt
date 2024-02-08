package com.mertyigit0.habittrackereasy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.habittrackereasy.databinding.HabitRowBinding
import java.util.Calendar

class HabitAdapter(private val habitList: ArrayList<Habit>) : RecyclerView.Adapter<HabitAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: HabitRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.incrementButton.setOnClickListener {
                val habitId = habitList[adapterPosition].id
                val graphView = binding.root.findViewById<GraphView>(R.id.graphView)
                incrementButtonClicked(graphView, habitId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HabitRowBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val habit = habitList[position]
        holder.binding.habitName.text = habit.name
        holder.binding.habitDescription.text = habit.description
        // Burada CalendarView için gerekli işlemleri yapabilirsiniz
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    private fun incrementButtonClicked(graphView: GraphView, habitId: Long) {
        val numRows = graphView.getNumRows()
        val numCols = graphView.getNumCols()
        val data = graphView.getData()

        val currentDate = Calendar.getInstance()
        val daysDiffInMillis = currentDate.timeInMillis - graphView.getStartDateMillis()
        val daysDiff = (daysDiffInMillis / (1000 * 60 * 60 * 24)).toInt()

        val col = daysDiff / numRows
        val row = daysDiff % numRows

        if (col < numCols && row < numRows) {
            data[row][col] = true
            val databaseHelper = DatabaseHelper(graphView.context)
            databaseHelper.addHabitData(habitId, currentDate.timeInMillis, true)
        }

        graphView.invalidate()
    }
}

