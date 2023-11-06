package com.example.sweatsink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class NewWeeklyScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_weekly_schedule_activity)

        val createButton = findViewById<Button>(R.id.buttonNewWeeklyScheduleCreate)
        createButton.setOnClickListener{
            val goalTextEdit=findViewById<EditText>(R.id.editTextNewWeeklyScheduleGoal)
            val equipmentTextEdit=findViewById<EditText>(R.id.editTextNewWeeklyScheduleEquipment)
            println(goalTextEdit.text)
            println(equipmentTextEdit.text)
        }
    }
}
