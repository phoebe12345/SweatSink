package com.example.sweatsink

import WeekPlan
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.File

class WeeklyScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weekly_schedule_activity)

        /*val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)
        println(day)*/

        val file = File(this.filesDir,"weekly_schedule.txt")
        val weekPlan=WeekPlan(file.readText())

        /*val weeklyScheduleTextView=findViewById<TextView>(R.id.textViewNewWeeklyScheduleContent)
        weeklyScheduleTextView.text=weekPlan.toString()*/

        val parentLayout=findViewById<View>(R.id.weeklyScheduleContentLayout) as LinearLayout
        for(i in 0..6){
            val dayPlan=weekPlan.days[i]
            if (dayPlan != null) {
                /*val dayCheckBox=CheckBox(this)
                dayCheckBox.text=weekPlan.getDayAsString(i)
                parentLayout.addView(dayCheckBox)*/
                val dayText=TextView(this)
                dayText.text=weekPlan.getDayAsString(i)
                parentLayout.addView(dayText)
                if(dayPlan.isRest){
                    val restText=TextView(this)
                    restText.text="rest day"
                    parentLayout.addView(restText)
                }else {
                    for (j in 0 until dayPlan.workouts.size) {
                        val checkBox = CheckBox(this)
                        checkBox.text = dayPlan.workouts[j].toString()
                        parentLayout.addView(checkBox)
                    }
                }
            }
        }

        val newWeeklyScheduleButton = findViewById<Button>(R.id.buttonNewWeeklySchedule)
        newWeeklyScheduleButton.setOnClickListener{
            val intent = Intent(this, NewWeeklyScheduleActivity::class.java)
            startActivity(intent)
        }
    }
}
