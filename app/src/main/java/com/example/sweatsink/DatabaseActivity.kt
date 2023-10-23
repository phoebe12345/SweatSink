package com.example.sweatsink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class DatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.database_activity)

        val databaseExercisesButtonClick = findViewById<Button>(R.id.database_exercises)
        databaseExercisesButtonClick.setOnClickListener{
            val intent = Intent(this, DatabaseExercisesActivity::class.java)
            startActivity(intent)
        }
    }
}
