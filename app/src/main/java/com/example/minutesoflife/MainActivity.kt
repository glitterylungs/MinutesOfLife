package com.example.minutesoflife

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var selectedDateTextView: TextView? = null
    private var minutesTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectDateButton: Button = findViewById(R.id.selectDateButton)
        selectedDateTextView = findViewById(R.id.selectedDateTextView)
        minutesTextView = findViewById(R.id.minutesTextView)

        selectDateButton.setOnClickListener {
            selectDateButtonPressed()
        }
    }

    private fun selectDateButtonPressed() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val datePicker = DatePickerDialog(this, {_, selectedYear, selectedMonth, selectedDay ->
            // What will happen when I chose the date and click "OK"

            // Display date
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            selectedDateTextView?.text = selectedDate

            //Create object of selected date and current date in specified format
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = dateFormat.parse(selectedDate)

            // if theDate is not empty
            theDate?.let {
                val currentDate = dateFormat.parse(dateFormat.format(System.currentTimeMillis()))

                // Change time passed from selected date into minutes
                // .time give us milliseconds which passed since the January 1, 1970
                val selectedDateInMinutes = theDate.time / 60000
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000

                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                    minutesTextView?.text = differenceInMinutes.toString()
                }
            }
        }, year, month, day)

        // Disable future dates
        datePicker.datePicker.maxDate = System.currentTimeMillis() - 86400000

        // Show a Date Picker View
        datePicker.show()

    }
}