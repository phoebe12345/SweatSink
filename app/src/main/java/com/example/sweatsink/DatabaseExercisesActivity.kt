package com.example.sweatsink

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File

class DatabaseExercisesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var currentIndex=0

        //setContentView(R.layout.database_exercises_activity)
        setContent{
            var textContent by remember { mutableStateOf( "saved data appears here...") }
            val context= LocalContext.current
            Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.End){
                //Text(text = textContent,modifier=Modifier.padding(16.dp))
                TextField(value=textContent, onValueChange = {
                    textContent=it
                }, modifier=Modifier.padding(16.dp).width(200.dp).height(500.dp))
            }
            Row(modifier=Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End){
                Button(onClick={
                    val file=File(context.filesDir,"saved_exercise_$currentIndex.txt")
                    file.writeText(textContent)
                }){
                    Text("Save Changes")
                }
            }
            Column {
                Text(text = "Saved Exercises", fontSize = 34.sp)
                for(i in 1..10) {
                    Button(onClick = {
                        val file = File(context.filesDir,"saved_exercise_$i.txt")
                        textContent=file.readText()
                        currentIndex=i
                    }){
                        Text("Exercise $i")
                    }
                }
            }
        }

        //INTERNAL STORAGE
        //val file = File(this.filesDir,"myfile.txt")
        //file.writeText("yo this is a file!!!!!!!!!!")
        //println(file.readText())

        /*val file = File(this.filesDir,"saved_exercises.txt")
        val content = findViewById<TextView>(R.id.textViewDatabaseExercisesContent)
        content.text = file.readText()

        val databaseExercisesSubmitButtonClick = findViewById<Button>(R.id.database_exercises_submit)
        databaseExercisesSubmitButtonClick.setOnClickListener{
            val userInput = findViewById<EditText>(R.id.editTextDatabaseExercisesInput)
            file.writeText(userInput.text.toString())
            content.text = file.readText()
        }*/
    }
}
