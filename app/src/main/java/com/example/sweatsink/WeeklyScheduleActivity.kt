package com.example.sweatsink

import android.R.id
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
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

        /*val parentLayout=findViewById<View>(R.id.weeklyScheduleContentLayout) as LinearLayout

        val checkBox = CheckBox(this)
        checkBox.text = "text"

        parentLayout.addView(checkBox)*/

        val newWeeklyScheduleButton = findViewById<Button>(R.id.buttonNewWeeklySchedule)
        newWeeklyScheduleButton.setOnClickListener{
            val intent = Intent(this, NewWeeklyScheduleActivity::class.java)
            startActivity(intent)
        }
    }
}
