package com.example.sweatsink

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.io.File




class AssistantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assistant_activity)


        val textOutput=findViewById<TextView>(R.id.textViewAssistantOutput)

        val sendButton = findViewById<Button>(R.id.buttonSend)
        sendButton.setOnClickListener{
            val textInput=findViewById<EditText>(R.id.editTextAssistantInput)
            val question=textInput.text.toString()
            val assistant=Assistant()
            assistant.getReply(question){ response ->
                runOnUiThread{
                    textOutput.text=response
                }
            }
        }

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener{
            textOutput.text = "Response will appear here..."
        }

        val saveButton=findViewById<Button>(R.id.buttonSave)
        saveButton.setOnClickListener {
            val numExercisesFile=File(this.filesDir,"num_exercises.txt")
            var numExercises=0
            if(numExercisesFile.isFile)
                numExercises=numExercisesFile.readText().toInt()
            if(numExercises<14){
                numExercises++
                numExercisesFile.writeText((numExercises).toString())
                val exerciseFile=File(this.filesDir,"saved_exercise_$numExercises.txt")
                exerciseFile.writeText(textOutput.text.toString())
            }else{
                Toast.makeText(this,"Can't save exercise, you're at your maximum amount!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
