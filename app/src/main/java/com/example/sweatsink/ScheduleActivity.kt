package com.example.sweatsink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class ScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_activity)

        val calendarButton = findViewById<Button>(R.id.buttonCalendar)
        calendarButton.setOnClickListener{
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        val weeklyScheduleButton = findViewById<Button>(R.id.buttonWeeklySchedule)
        weeklyScheduleButton.setOnClickListener{
            val intent = Intent(this, WeeklyScheduleActivity::class.java)
            startActivity(intent)
        }
    }
}
