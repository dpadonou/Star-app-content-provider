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
import com.downloader.PRDownloader
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

        /*val f = FillDatabase(this, application)
        f.fillDatabase()*/

        val intent = intent
        if (intent.extras != null) {
            val link = intent.extras!!.getString("link")
            val path = intent.extras!!.getString("path")
            download = DownloadZip(this, application)
            download.downloadZip(link.toString(), Utils(this).zipName, path.toString())
        } else {
            if (savedInstanceState != null) {
                restoreInstance(savedInstanceState)
            }
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
                return getString(R.string.mois_janvier)
            }
            2 -> {
                return getString(R.string.mois_fevrier)
            }
            3 -> {
                return getString(R.string.mois_mars)
            }
            4 -> {
                return getString(R.string.mois_avril)
            }
            5 -> {
                return getString(R.string.mois_mai)
            }
            6 -> {
                return getString(R.string.mois_juin)
            }
            7 -> {
                return getString(R.string.mois_juillet)
            }
            8 -> {
                return getString(R.string.mois_aout)
            }
            9 -> {
                return getString(R.string.mois_septembre)
            }
            10 -> {
                return getString(R.string.mois_octobre)
            }
            11 -> {
                return getString(R.string.mois_novembre)
            }
            12 -> {
                return getString(R.string.mois_decembre)
            }
            else -> {
                return getString(R.string.mois_errors)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("downloadProcessId", download.downloaderId)
        PRDownloader.pause(download.downloaderId)
        super.onSaveInstanceState(outState)
    }

    private fun restoreInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val id = savedInstanceState.getInt("downloadProcessId")
            PRDownloader.resume(id)
        }
    }

}