package com.example.sweatsink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.File

class WeeklyScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weekly_schedule_activity)

        val weeklyScheduleTextView=findViewById<TextView>(R.id.textViewNewWeeklyScheduleContent)
        val file = File(this.filesDir,"weekly_schedule.txt")
        weeklyScheduleTextView.text=file.readText()

        val newWeeklyScheduleButton = findViewById<Button>(R.id.buttonNewWeeklySchedule)
        newWeeklyScheduleButton.setOnClickListener{
            val intent = Intent(this, NewWeeklyScheduleActivity::class.java)
            startActivity(intent)
        }
    }
}
