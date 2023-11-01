package com.example.sweatsink

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.ComponentActivity


class CalendarActivity : ComponentActivity() {

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_activity)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        calendarView.setOnDateChangeListener{
            calendarView, i, j, k ->
            year = i
            month = j
            day = k

            val message = "Selected Date $year-$month-$day"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        val scheduleButton = findViewById<Button>(R.id.CalendarScheduleButton)
        scheduleButton.setOnClickListener{
            // Save Date into local DB
            val message = "Scheduled run for $year-$month-$day"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}