package com.example.sweatsink

import android.content.Context
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

        //val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        /*var editor = sharedPreference.edit()
        editor.putString("username","Anupam")
        editor.putLong("l",100L)
        editor.commit()*/
        //sharedPreference.getLong("l",1L)
        //println(sharedPreference.getString("username","defaultName"))
    }
}
