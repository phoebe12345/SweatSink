package com.example.sweatsink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.widget.Button
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sweatsink.ui.theme.SweatSinkTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

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

            val calendarButton = findViewById<Button>(R.id.calendar)
            calendarButton.setOnClickListener{
                val intent = Intent(this, CalendarActivity::class.java)
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