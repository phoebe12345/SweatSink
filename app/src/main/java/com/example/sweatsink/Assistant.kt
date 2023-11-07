package com.example.sweatsink

import android.util.Log
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
import java.io.IOException

class Assistant {
    fun getReply(message:String, callback:(String) -> Unit){
        val client = OkHttpClient()

        println(message)

        val url="https://api.openai.com/v1/completions"
        val key = BuildConfig.AI_API_KEY
        val requestText="""
                    {
                    "model": "gpt-3.5-turbo-instruct",
                    "prompt": "$message",
                    "max_tokens": 1000,
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
                    println(body)
                }
                else{
                    println("no reply")
                }
                try {
                    var jsonObject= JSONObject(body)
                    val jsonArray: JSONArray = jsonObject.getJSONArray("choices")
                    val textResult=jsonArray.getJSONObject(0).getString("text")
                    callback(textResult)
                }
                catch(e: JSONException){
                    val message="OpenAI API error!"
                    println(message)
                    callback(message)
                }
            }
        })
    }
}