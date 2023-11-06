package com.example.sweatsink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class WeeklyScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weekly_schedule_activity)

        val newWeeklyScheduleButton = findViewById<Button>(R.id.buttonNewWeeklySchedule)
        newWeeklyScheduleButton.setOnClickListener{
            val intent = Intent(this, NewWeeklyScheduleActivity::class.java)
            startActivity(intent)
        }
    }
}