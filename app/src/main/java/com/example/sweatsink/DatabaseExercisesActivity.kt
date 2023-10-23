package com.example.sweatsink

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.File

class DatabaseExercisesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.database_exercises_activity)

        //INTERNAL STORAGE
        val file = File(this.filesDir,"myfile.txt")
        //file.writeText("yo this is a file!!!!!!!!!!")
        println(file.readText())

        val content = findViewById<TextView>(R.id.textViewDatabaseExercisesContent)
        content.text = file.readText()
    }
}
