package fr.istic.mob.starapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.os.HandlerThread
import android.os.IBinder
import android.os.Process
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.File
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.getSystemService


class CheckStar : Service() {
    private var firstUrl: String = ""

    override fun onCreate() {
        val thread = HandlerThread(
            "ServiceStartArguments",
            Process.THREAD_PRIORITY_BACKGROUND
        )
        thread.start()
        // Log.d("onCreate()", "After service created")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getUrl()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUrl() {
        val path = Utils(applicationContext).directoryPath
        val dir = File(path)
        /** Créer le repertoire si il n'existe pas **/
        if (!dir.exists()) {
            dir.mkdirs();
        }
        val oldPref = this.getSharedPreferences("MyPref", 0)
        var link = ""
        val url = "https://data.explore.star.fr/api/records/1.0/search/?dataset=tco-busmetro-horaires-gtfs-versions-td&q="
        val req = JsonObjectRequest(Request.Method.GET, url, null, {
            val v = it.getJSONArray("records").get(0) as JSONObject
            link = (v.get("fields") as JSONObject).getString("url")
            var oldLink = oldPref.getString("link", null)
            if (oldLink == null) {
                /** Ajout de l'url dans les preferences partagées **/
                val settings = applicationContext.getSharedPreferences("MyPref", 0)
                val editor = settings.edit()
                editor.putString("link", link)
                editor.apply()
                /** Telechargement du zip la premiere fois **/
                Log.i("link", link)
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("link", link)
                intent.putExtra("path", path)
                intent.flags = FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
            } else {
                notifyMyApp(applicationContext, link, path)
                Log.i("link", link)
                if (oldLink != link) {
                    oldLink = link
                    /** Ajout du nouveau lien dans les preferneces partagées **/
                    val settings = applicationContext.getSharedPreferences("MyPref", 0)
                    val editor = settings.edit()
                    editor.putString("link", oldLink)
                    editor.apply()
                    /** Notification de l'application **/
                    notifyMyApp(applicationContext, link, path)
                }
            }
        },
            { Log.i("", it.printStackTrace().toString()) }
        )
        Volley.newRequestQueue(applicationContext).add(req)
    }

    /** Notifiez l'application d'un nouveau lien **/
    @RequiresApi(Build.VERSION_CODES.O)
    private fun notifyMyApp(context: Context, link: String, path: String) {
        val CHANNEL_ID = "channel1"
        /** Création de la chaine pour la notification **/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,"StarDP",NotificationManager.IMPORTANCE_HIGH)
            val manager  = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Star DP")
                .setContentText("Mise à jour disponible !")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setChannelId(CHANNEL_ID)
                .build()
        val downloadIntent = Intent(applicationContext, MainActivity::class.java)
        downloadIntent.flags = Intent.FLAG_ACTIVITY_TASK_ON_HOME
        downloadIntent.putExtra("link", link)
        downloadIntent.putExtra("path", path)
        val stackBuilder = TaskStackBuilder.create(applicationContext)
        stackBuilder.addNextIntent(downloadIntent)
        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        notification.contentIntent = pendingIntent
        val nM = NotificationManagerCompat.from(context)
        nM.notify(1,notification)
    }
}
