package com.example.sweatsink

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException

class AssistantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assistant_activity)

        val sendButtonClick = findViewById<Button>(R.id.buttonSend)
        sendButtonClick.setOnClickListener{
            val textOutput=findViewById<TextView>(R.id.textViewAssistantOutput)
            val textInput=findViewById<EditText>(R.id.editTextAssistantInput)
            val question=textInput.text.toString()
            getReply(question){ response ->
                runOnUiThread{
                    textOutput.text=response
                }
            }
        }
    }

    fun getReply(message:String, callback:(String) -> Unit){
        val client = OkHttpClient()

        Log.v("data",message)

        val url="https://api.openai.com/v1/completions"
        val key=""
        //val key=getKey()
        Log.v("data","KEY: $key")
        val requestText="""
                    {
                    "model": "gpt-3.5-turbo-instruct",
                    "prompt": "$message",
                    "max_tokens": 7,
                    "temperature": 0
                    }
                """.trimIndent()
        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $key")
            .post(requestText.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "API failure", e)
            }
            override fun onResponse(call: Call, response: Response) {
                val body=response.body?.string()
                if (body != null) {
                    Log.v("data",body)
                }
                else{
                    Log.v("data","no reply")
                }
                var jsonObject= JSONObject(body)
                try {
                    val jsonArray: JSONArray = jsonObject.getJSONArray("choices")
                    val textResult=jsonArray.getJSONObject(0).getString("text")
                    callback(textResult)
                }
                catch(e: JSONException){
                    val message="OpenAI API error"
                    println(message)
                    callback(message)
                }
            }
        })
    }
}
