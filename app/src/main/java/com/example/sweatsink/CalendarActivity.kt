package com.example.sweatsink

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.util.Calendar


class CalendarActivity : ComponentActivity() {

    private val calendar: Calendar = Calendar.getInstance()
    private val currentYear = calendar.get(Calendar.YEAR)
    private val currentMonth = calendar.get(Calendar.MONTH)+1
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    private var year: Int = currentYear
    private var month: Int = currentMonth
    private var day: Int = currentDay
    private var schedule:MutableSet<ArrayList<Int>> = mutableSetOf(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_activity)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val textViewSchedule = findViewById<TextView>(R.id.textViewSchedule)


        calendarView.setOnDateChangeListener{
            calendarView, i, j, k ->
            year = i
            month = j+1
            day = k

            val message = "Selected Date $year-$month-$day"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        val scheduleButton = findViewById<Button>(R.id.CalendarScheduleButton)
        scheduleButton.setOnClickListener{
            textViewSchedule.text = ""
            if (year > currentYear || (year == currentYear && month > currentMonth) ||
                (year == currentYear && month == currentMonth && day >= currentDay)){

                schedule.add(arrayListOf(year,month,day))
                val message = "Scheduled exercise for $year-$month-$day"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            } else{
                val message = "Cannot Schedule on $year-$month-$day!"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

        }

        val clearScheduleButton = findViewById<Button>(R.id.ClearScheduleButton)
        clearScheduleButton.setOnClickListener {
            textViewSchedule.text = ""
            schedule.clear()
            val message = "Schedule Cleared!"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        val viewScheduleButton = findViewById<Button>(R.id.ViewScheduleButton)
        viewScheduleButton.setOnClickListener {

            var message = ""
            schedule.forEach { arr ->
                arr.forEach{
                     message += "$it-"
                 }
                message = message.dropLast(1)
                message += "\n"
            }
            textViewSchedule.text = message
        }
    }
}