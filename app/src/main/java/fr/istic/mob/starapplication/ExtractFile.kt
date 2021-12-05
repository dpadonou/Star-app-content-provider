package fr.istic.mob.starapplication

import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.HandlerThread
import android.os.IBinder
import android.os.Process
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

class ExtractFile(): Service() {
    private fun extract(targetPath: String, destinationPath: String) {
        Toast.makeText(applicationContext,"extraction des fichiers", Toast.LENGTH_LONG).show()
        try {
            val f: File = File(targetPath)
            if (!f.isDirectory){
                f.mkdirs()
            }
            ZipFile(f).use { it ->
                it.entries()
                    .asSequence()
                    .filter { !it.isDirectory }
                    .forEach { fl ->
                        if ( Utils(applicationContext).files.contains(fl.name) ){
                            val currFile = File(destinationPath, fl.name)
                            currFile.parentFile?.mkdirs()
                            it.getInputStream(fl).use { input ->
                                currFile.outputStream().use { output ->
                                    input.copyTo(output)
                                }
                            }
                        }
                    }
            }
            f.delete()
            val intent = Intent(applicationContext, FillDatabase::class.java)
            applicationContext.startService(intent)
        }catch (e: IOException){
           Log.i("",e.printStackTrace().toString())
        }


    }
    override fun onCreate() {
        val thread = HandlerThread(
            "ServiceStartArguments",
            Process.THREAD_PRIORITY_BACKGROUND
        )
        thread.start()
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.extras != null) {
            val target = intent.extras!!.getString("target").toString()
            val destination =Utils(applicationContext).directoryPath
            extract(target,destination)
        }
        return START_STICKY
    }
}