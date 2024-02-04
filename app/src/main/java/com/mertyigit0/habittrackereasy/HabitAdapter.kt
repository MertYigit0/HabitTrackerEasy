package com.mertyigit0.habittrackereasy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.habittrackereasy.databinding.HabitRowBinding

class HabitAdapter(private val habitList: ArrayList<Habit>) : RecyclerView.Adapter<HabitAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: HabitRowBinding, private val adapter: HabitAdapter) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.incrementButton.setOnClickListener {
                // Burada butona tıklama işlemini gerçekleştir
                // Geçerli tarihi boyamak gibi
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HabitRowBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding, this)
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
}
