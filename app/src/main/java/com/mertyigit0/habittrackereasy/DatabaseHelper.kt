package com.mertyigit0.habittrackereasy

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HabitTracker.db"

        // Habit table
        private const val TABLE_HABIT = "Habit"
        private const val COLUMN_HABIT_ID = "id"
        private const val COLUMN_HABIT_NAME = "name"
        private const val COLUMN_HABIT_DESCRIPTION = "description"

        // HabitData table
        private const val TABLE_HABIT_DATA = "HabitData"
        private const val COLUMN_HABIT_DATA_ID = "id"
        private const val COLUMN_HABIT_DATA_HABIT_ID = "habitId"
        private const val COLUMN_HABIT_DATA_DATE = "date"
        private const val COLUMN_HABIT_DATA_VALUE = "value"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_HABIT_TABLE = ("CREATE TABLE $TABLE_HABIT ($COLUMN_HABIT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_HABIT_NAME TEXT, $COLUMN_HABIT_DESCRIPTION TEXT)")
        db.execSQL(CREATE_HABIT_TABLE)

        val CREATE_HABIT_DATA_TABLE = ("CREATE TABLE $TABLE_HABIT_DATA ($COLUMN_HABIT_DATA_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_HABIT_DATA_HABIT_ID INTEGER, $COLUMN_HABIT_DATA_DATE INTEGER, $COLUMN_HABIT_DATA_VALUE INTEGER, FOREIGN KEY($COLUMN_HABIT_DATA_HABIT_ID) REFERENCES $TABLE_HABIT($COLUMN_HABIT_ID))")
        db.execSQL(CREATE_HABIT_DATA_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HABIT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HABIT_DATA")
        onCreate(db)
    }

    fun addHabit(habit: Habit): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_HABIT_NAME, habit.name)
        values.put(COLUMN_HABIT_DESCRIPTION, habit.description)
        val id = db.insert(TABLE_HABIT, null, values)
        db.close()
        return id
    }

    fun addHabitData(habitId: Long, date: Long, value: Boolean) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_HABIT_DATA_HABIT_ID, habitId)
        values.put(COLUMN_HABIT_DATA_DATE, date)
        values.put(COLUMN_HABIT_DATA_VALUE, if (value) 1 else 0)
        db.insert(TABLE_HABIT_DATA, null, values)
        db.close()
    }

    // Diğer işlemler için gerekli fonksiyonlar da buraya eklenebilir (veri güncelleme, silme, sorgulama vb.)

    @SuppressLint("Range")
    fun getAllHabitsWithData(): List<Habit> {
        val habitList = mutableListOf<Habit>()

        val selectQuery = "SELECT * FROM $TABLE_HABIT"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val habit = Habit(
                    id = cursor.getLong(cursor.getColumnIndex(COLUMN_HABIT_ID)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_HABIT_NAME)),
                    description = cursor.getString(cursor.getColumnIndex(COLUMN_HABIT_DESCRIPTION)),
                    habitDataList = getHabitDataList(cursor.getLong(cursor.getColumnIndex(COLUMN_HABIT_ID)))
                )
                habitList.add(habit)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return habitList
    }

    @SuppressLint("Range")
    private fun getHabitDataList(habitId: Long): List<Habit.HabitData> {
        val habitDataList = mutableListOf<Habit.HabitData>()

        val selectQuery = "SELECT * FROM $TABLE_HABIT_DATA WHERE $COLUMN_HABIT_DATA_HABIT_ID = $habitId"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val habitData = Habit.HabitData(
                    date = cursor.getLong(cursor.getColumnIndex(COLUMN_HABIT_DATA_DATE)),
                    value = cursor.getInt(cursor.getColumnIndex(COLUMN_HABIT_DATA_VALUE)) == 1
                )
                habitDataList.add(habitData)
            } while (cursor.moveToNext())
        }

        cursor.close()

        return habitDataList
    }

}
