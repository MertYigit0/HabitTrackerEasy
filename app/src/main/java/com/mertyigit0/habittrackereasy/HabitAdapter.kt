package com.mertyigit0.habittrackereasy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.habittrackereasy.databinding.HabitRowBinding
import java.util.Calendar

class HabitAdapter(private val habitList: ArrayList<Habit>) : RecyclerView.Adapter<HabitAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: HabitRowBinding, private val adapter: HabitAdapter) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.incrementButton.setOnClickListener {
                // Burada butona tıklama işlemini gerçekleştir
                // Geçerli tarihi boyamak gibi
                val graphView = itemView.findViewById(R.id.graphView) as GraphView


                // incrementButtonClicked fonksiyonunu çağır
                incrementButtonClicked(graphView)
            }
        }
       // val startMillis = graphView.getStartDateMillis()
        private fun incrementButtonClicked(graphView: GraphView) {
            val numRows = graphView.getNumRows()
            val numCols = graphView.getNumCols()
            val data = graphView.getData()



            // Güncel tarihi al
            val currentDate = Calendar.getInstance()
            val startDate = graphView.getStartDate()
           // Güncel tarihi al

           println("Güncel Tarih: ${currentDate.time}")

            // Başlangıç tarihinden bugüne kadar geçen gün sayısını hesapla
            val daysDiffInMillis = currentDate.timeInMillis -graphView.getStartDateMillis()
            val daysDiff = (daysDiffInMillis / (1000 * 60 * 60 * 24)).toInt()

            // Hücrenin indekslerini hesapla
            val col = daysDiff / numRows
            val row = daysDiff % numRows

            // Hücrenin değerini true yap
            if (col < numCols && row < numRows) {
                data[row][col] = true
            }

            // Grafiği güncelle
            graphView.invalidate()
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
