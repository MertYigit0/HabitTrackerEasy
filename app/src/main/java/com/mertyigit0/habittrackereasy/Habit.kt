package com.mertyigit0.habittrackereasy

data class Habit(
    val name: String, // Alışkanlığın adı
    val description: String, // Alışkanlığın açıklaması
    var currentStreak: Int = 0, // Mevcut alışkanlık serisi
    var bestStreak: Int = 0 // En iyi alışkanlık serisi
)

