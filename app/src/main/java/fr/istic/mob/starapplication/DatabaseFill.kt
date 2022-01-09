package fr.istic.mob.starapplication

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import fr.istic.mob.starapplication.models.*
import fr.istic.mob.starapplication.viewModel.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.stream.Stream
import java.util.zip.ZipFile
import kotlin.streams.toList


@RequiresApi(Build.VERSION_CODES.N)
class DatabaseFill: JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        val task = FillTask(applicationContext,application)
        task.execute(1)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
   companion object {
       fun scheduleJob(context: Context) {
           val serviceComponent = ComponentName(context, DatabaseFill::class.java)
           val jobInbo = JobInfo.Builder(0, serviceComponent)
               .setMinimumLatency(1000) // Temps d'attente minimal avant déclenchement
               .setOverrideDeadline(3000) // Temps d'attente maximal avant déclenchement
               .build()
           val jobScheduler: JobScheduler = context.getSystemService(JobScheduler::class.java)
           jobScheduler.schedule(jobInbo)
       }
   }

}