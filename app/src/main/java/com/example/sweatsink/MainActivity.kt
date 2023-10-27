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
                val client=OkHttpClient()

                val question = "what's your name?"
                Log.v("data",question)

                val apiKey="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"
                val url="https://api.openai.com/v1/completions"
                val requestBody="""
                    {
                    "model": "gpt-3.5-turbo-instruct",
                    "prompt": "$question",
                    "max_tokens": 7,
                    "temperature": 0
                    }
                """.trimIndent()
                val request = Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer $apiKey")
                    .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("error", "API failed", e)
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val body=response.body?.string()
                        if (body != null) {
                            Log.v("data",body)
                        }
                        else{
                            Log.v("data","empty")
                        }
                        /*var jsonObject= JSONObject(body)
                        val jsonArray: JSONArray =jsonObject.getJSONArray("choices")
                        val textResult=jsonArray.getJSONObject(0).getString("text")
                        callback(textResult)*/
                    }
                })

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