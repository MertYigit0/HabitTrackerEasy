package com.mertyigit0.habittrackereasy

data class Habit(
    var id: Long = -1,
    val name: String,
    val description: String,
    val habitDataList: List<HabitData> = listOf()
) {
    data class HabitData(
        val date: Long,
        val value: Boolean
    )
}
