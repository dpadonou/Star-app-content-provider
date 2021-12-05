package fr.istic.mob.starapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.AlarmManager

import android.app.PendingIntent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class MyAlarm: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val intent = Intent(p0, CheckStar::class.java)
        p0!!.startService(intent)
        Log.i("","Service lancer")
        setAlarm(p0)
    }

    fun setAlarm(context: Context) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, MyAlarm::class.java)
        val pi = PendingIntent.getBroadcast(context, 0, i, 0)
        am.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            (System.currentTimeMillis() / 1000L + 15L) * 1000L,
            pi
        ) //Next alarm in 15s
    }
}