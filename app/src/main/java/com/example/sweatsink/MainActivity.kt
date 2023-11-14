package com.example.sweatsink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.widget.Button
import android.content.Intent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.main_activity)

            val buttonClick = findViewById<Button>(R.id.map)
            buttonClick.setOnClickListener{
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }

            val scheduleButton = findViewById<Button>(R.id.calendar)
            scheduleButton.setOnClickListener{
                val intent = Intent(this, ScheduleActivity::class.java)
                startActivity(intent)
            }

            val databaseButtonClick = findViewById<Button>(R.id.database)
            databaseButtonClick.setOnClickListener{
                val intent = Intent(this, DatabaseActivity::class.java)
                startActivity(intent)
            }

            val assistantButtonClick = findViewById<Button>(R.id.chat_gpt)
            assistantButtonClick.setOnClickListener {
                val intent = Intent(this, AssistantActivity::class.java)
                startActivity(intent)
            }

//            SweatSinkTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SweatSinkTheme {
//        Greeting("Android")
//    }
//}