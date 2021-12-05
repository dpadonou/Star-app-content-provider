package fr.istic.mob.starapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var dateDialog: DatePickerDialog
    private lateinit var timeDialog: TimePickerDialog
    private lateinit var btnDate: Button
    private lateinit var btnTime: Button

    private var day = 0
    private var month = 0
    private var year = 0
    private var minutes = 0
    private var hours = 0

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDate = findViewById(R.id.chooseDateBtn)
        btnTime = findViewById(R.id.chooseHourBtn)

        val alarm: MyAlarm = MyAlarm()
        alarm.setAlarm(this)
        /** Lancer un service grace à la notification **/
        val intent = intent
        if (intent.extras != null) {
            this.startService(intent)
        }

        setPickers()
    }

    private fun setPickers() {
        btnDate.setOnClickListener {
            getCalendarObject()
            DatePickerDialog(this, this, year, month, day).show()
        }

        btnTime.setOnClickListener{
            getCalendarObject()
            TimePickerDialog(this, this, hours, minutes, true).show()
        }
    }

    private fun getCalendarObject() {
        val cal: Calendar = Calendar.getInstance()

        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        minutes = cal.get(Calendar.MINUTE)
        hours = cal.get(Calendar.HOUR_OF_DAY)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val newText = "$dayOfMonth ${formatMonth(month)} $year"
        btnDate.text = newText
    }

    private fun formatMonth(month: Int): String {
        when (month + 1) {
            1 -> {
                return "Janvier"
            }
            2 -> {
                return "Fevrier"
            }
            3 -> {
                return "Mars"
            }
            4 -> {
                return "Avril"
            }
            5 -> {
                return "Mai"
            }
            6 -> {
                return "Juin"
            }
            7 -> {
                return "Juillet"
            }
            8 -> {
                return "Août"
            }
            9 -> {
                return "Septembre"
            }
            10 -> {
                return "Octobre"
            }
            11 -> {
                return "Novembre"
            }
            12 -> {
                return "Decembre"
            }
            else -> {
                return "####"
            }
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val newText = "$hourOfDay : $minute"
        btnTime.text = newText
    }

}