package com.example.sweatsink

import android.os.Bundle
import androidx.activity.ComponentActivity

class NewWeeklyScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_weekly_schedule_activity)

        /*val databaseExercisesButtonClick = findViewById<Button>(R.id.database_exercises)
        databaseExercisesButtonClick.setOnClickListener{
            val intent = Intent(this, DatabaseExercisesActivity::class.java)
            startActivity(intent)
        }*/
    }
}