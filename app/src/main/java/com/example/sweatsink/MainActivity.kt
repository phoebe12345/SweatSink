package com.example.sweatsink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.widget.Button
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sweatsink.ui.theme.SweatSinkTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.main_activity)

            val buttonClick = findViewById<Button>(R.id.map)
            buttonClick.setOnClickListener{
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
            }

            val calendarButton = findViewById<Button>(R.id.calendar)
            calendarButton.setOnClickListener{
                val intent = Intent(this, CalendarActivity::class.java)
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