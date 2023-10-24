package com.example.sweatsink

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.File

class DatabaseExercisesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.database_exercises_activity)

        //INTERNAL STORAGE
        //val file = File(this.filesDir,"myfile.txt")
        //file.writeText("yo this is a file!!!!!!!!!!")
        //println(file.readText())

        val file = File(this.filesDir,"saved_exercises.txt")
        val content = findViewById<TextView>(R.id.textViewDatabaseExercisesContent)
        content.text = file.readText()

        val databaseExercisesSubmitButtonClick = findViewById<Button>(R.id.database_exercises_submit)
        databaseExercisesSubmitButtonClick.setOnClickListener{
            val userInput = findViewById<EditText>(R.id.editTextDatabaseExercisesInput)
            file.writeText(userInput.text.toString())
            content.text = file.readText()
        }
    }
}
