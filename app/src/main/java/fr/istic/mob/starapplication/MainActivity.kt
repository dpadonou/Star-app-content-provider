package fr.istic.mob.starapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

class MainActivity : AppCompatActivity() {

    private var datePickerDialog: DatePickerDialog? = null
    private var timePickerDialog: TimePickerDialog? = null
    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var download: DownloadZip

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarm = MyAlarm()
        alarm.setAlarm(this)

        val intent = intent
        if (intent.extras != null) {
            val link = intent.extras!!.getString("link")
            val path = intent.extras!!.getString("path")
            download = DownloadZip(this, application)
            download.downloadZip(link.toString(), Utils(this).zipName, path.toString())
        }

        btnDate = findViewById(R.id.chooseDateBtn)
        btnTime = findViewById(R.id.chooseHourBtn)
        makeReady()
    }

    private fun setListener() {
        btnDate.setOnClickListener {
            datePickerDialog?.show()
        }

        btnTime.setOnClickListener {
            timePickerDialog?.show()
        }
    }

    private fun makeReady() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val hourOfDay = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        var newText = "$day ${formatMonth(month)} $year"
        btnDate.text = newText

        newText = "$hourOfDay : $minute"
        btnTime.text = newText

        setListener()
        initDatePicker()
        initTimePicker()
    }

    private fun initDatePicker() {
        val dateSetListener = OnDateSetListener { _, year, month, day ->
            val newText = "$day ${formatMonth(month)} $year"
            btnDate.text = newText
        }
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
    }

    private fun initTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val newText = "$hourOfDay : $minute"
            btnTime.text = newText
        }
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        timePickerDialog = TimePickerDialog(this, style, timeSetListener, hour, minute, true)
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

    override fun onSaveInstanceState(outState: Bundle) {
        val downloadProcess = Json.encodeToString(download)
        outState.putSerializable("downloadProcess", downloadProcess)
        super.onSaveInstanceState(outState)
    }

    private fun restoreInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val dp = savedInstanceState.getSerializable("downloadProcess")
            download = Json.decodeFromString("$dp")
        }else{

        }
    }

}