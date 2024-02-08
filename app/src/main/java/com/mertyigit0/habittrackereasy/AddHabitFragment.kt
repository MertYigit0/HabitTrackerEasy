package com.mertyigit0.habittrackereasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mertyigit0.habittrackereasy.databinding.FragmentAddHabitBinding
import com.mertyigit0.habittrackereasy.databinding.FragmentHabitListBinding

class AddHabitFragment : Fragment() {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerColor()
        spinnerIcon()
    }


    private  fun spinnerColor(){
        // Spinner için bir ArrayAdapter oluştur
        val adapter: ArrayAdapter<CharSequence>? = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.colorss,  // Spinner'a yüklenecek diziyi belirt
                android.R.layout.simple_spinner_item  // Spinner için basit bir görünüm kullan
            )
        }

// Spinner'a açılır listeyi bağla
        if (adapter != null) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerColor.adapter = adapter

        // Spinner'da bir öğe seçildiğinde ne yapılacağını belirle
        binding.spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Seçilen öğeyi al
                val selectedColor: String = parent.getItemAtPosition(position) as String
                // Seçilen öğeyi işle
                Toast.makeText(context, "Selected: $selectedColor", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Bir şey seçilmediğinde ne yapılacağını belirle
                // Örneğin, hiçbir işlem yapma veya bir uyarı mesajı gösterme
            }
        }
    }
    private  fun spinnerIcon(){
        // Spinner için bir ArrayAdapter oluştur
        val adapter: ArrayAdapter<CharSequence>? = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.icons,  // Spinner'a yüklenecek diziyi belirt
                android.R.layout.simple_spinner_item  // Spinner için basit bir görünüm kullan
            )
        }

// Spinner'a açılır listeyi bağla
        if (adapter != null) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerIcon.adapter = adapter

        // Spinner'da bir öğe seçildiğinde ne yapılacağını belirle
        binding.spinnerIcon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Seçilen öğeyi al
                val selectedColor: String = parent.getItemAtPosition(position) as String
                // Seçilen öğeyi işle
                Toast.makeText(context, "Selected: $selectedColor", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Bir şey seçilmediğinde ne yapılacağını belirle
                // Örneğin, hiçbir işlem yapma veya bir uyarı mesajı gösterme
            }
        }
    }


}