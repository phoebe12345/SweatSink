package com.example.sweatsink

import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File


class DatabaseExercisesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var currentIndex=0

        val numExercisesFile=File(this.filesDir,"num_exercises.txt")
        //numExercisesFile.writeText("10")

        fun getNumExercises():Int{
            var numExercises=1
            if(numExercisesFile.isFile) {
                numExercises = numExercisesFile.readText().toInt()
            }
            return numExercises
        }

        //setContentView(R.layout.database_exercises_activity)
        fun setTheContent(){
            setContent {
                var textContent by remember { mutableStateOf("Saved data appears here...") }
                if(getNumExercises()==0){
                    textContent="You don't have anything saved, try adding an exercise..."
                }

                val context = LocalContext.current

                /*val statFs = StatFs(Environment.getRootDirectory().absolutePath)
                val freeBytes = (statFs.blockSizeLong * statFs.availableBlocksLong)
                if(freeBytes<3000){
                    Toast.makeText(context,"You're running low on storage space!",Toast.LENGTH_SHORT).show()
                }*/

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    TextField(value = textContent, onValueChange = {
                        textContent = it
                    }, modifier = Modifier
                        .padding(16.dp)
                        .width(200.dp)
                        .height(500.dp))
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        val numExercises=getNumExercises()
                        if(numExercises>0){
                            numExercisesFile.writeText((numExercises - 1).toString())
                        }else{
                            Toast.makeText(context,"Can't remove exercise, there's nothing to remove!",
                                Toast.LENGTH_SHORT).show()
                        }
                        setTheContent()
                    }) {
                        Text("Remove")
                    }
                    Button(onClick = {
                        val numExercises = getNumExercises()
                        if (numExercises < 14) {//max 14 exercises
                            numExercisesFile.writeText((numExercises + 1).toString())
                        }else{
                            Toast.makeText(context,"Can't add exercise, you're at your maximum amount!",Toast.LENGTH_SHORT).show()
                        }
                        setTheContent()
                    }) {
                        Text("Add")
                    }
                    Button(onClick = {
                        val file = File(context.filesDir, "saved_exercise_$currentIndex.txt")
                        file.writeText(textContent)
                    }) {
                        Text("Save")
                    }
                }
                Column {
                    Text(text = "Saved Exercises", fontSize = 34.sp)
                    val numExercises = getNumExercises()
                    for (i in 1..numExercises) {
                        Button(onClick = {
                            val file = File(context.filesDir, "saved_exercise_$i.txt")
                            if (file.isFile) {
                                textContent = file.readText()
                            } else {
                                textContent = ""
                            }
                            currentIndex = i
                        }) {
                            Text("Exercise $i")
                        }
                    }
                }
            }
        }
        setTheContent()

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
