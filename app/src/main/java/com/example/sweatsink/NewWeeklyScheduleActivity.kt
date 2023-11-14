package com.example.sweatsink

import WeekPlan
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import java.io.File

class NewWeeklyScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_weekly_schedule_activity)

        val createButton = findViewById<Button>(R.id.buttonNewWeeklyScheduleCreate)
        createButton.setOnClickListener{
            val goalTextEdit=findViewById<EditText>(R.id.editTextNewWeeklyScheduleGoal)
            val goal=goalTextEdit.text.toString()
            val equipmentTextEdit=findViewById<EditText>(R.id.editTextNewWeeklyScheduleEquipment)
            val equipment=equipmentTextEdit.text.toString()
            val prompt="create a weekly fitness program that follows the following format: list the days of the week beginning with monday in all capitals, followed by a new line, without any additional formatting. after each day write a list of individual workouts following this format: #. workout name - # of sets, # of reps, rest time between sets in minutes. replace workout name with the name of the exercise. all exercises must follow this format. the program should attempt to achieve the following: $goal. the program is allowed to utilize the following equipment: $equipment."
            val assistant=Assistant()
            assistant.getReply(prompt){ response ->
                runOnUiThread{
                    val weekPlan=WeekPlan(response)
                    val file = File(this.filesDir,"weekly_schedule.txt")
                    file.writeText(weekPlan.toString())

                    val intent = Intent(this, WeeklyScheduleActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
