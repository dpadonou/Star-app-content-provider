package fr.istic.mob.starapplication

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import fr.istic.mob.starapplication.database.StarDatabase
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
class FillTask: AsyncTask<Int, Int, Boolean> {

    var context:Context
    var application:Application
    var builder: NotificationCompat.Builder
    val CHANNEL_ID = "channel1"
    var nM: NotificationManagerCompat

    constructor(context: Context,application: Application) : super(){
        this.context = context
        this.application = application
        builder = NotificationCompat.Builder(this.context,CHANNEL_ID)
        nM = NotificationManagerCompat.from(this.context)
    }

    override fun doInBackground(vararg params: Int?): Boolean{
        val target = Utils(context).directoryPath + Utils(context).separator+ Utils(context).zipName
        sendProgress()
        //Toast.makeText(Utils.getActivity(),"Debut de l'extraction",Toast.LENGTH_LONG).show()
        extract(target,Utils(context).directoryPath)
        //Toast.makeText(Utils.getActivity(),"Extraction terminée et début du remplissage de la base",Toast.LENGTH_LONG).show()
        fillDatabase()
        Log.i("","Fin du remplissage")
        Utils.stopActivity()

        return true
    }

    override fun onProgressUpdate(vararg values: Int?) {
        val progress = values[0]
        builder.setProgress(1,progress!!,true)

        nM.notify(1,builder.build())
    }

    override fun onPostExecute(result: Boolean?) {
       // nM.cancel(1)
       // Toast.makeText(context,"Extraction et remplissage de la base terminée", Toast.LENGTH_LONG).show()
    }

    private fun fillDatabase(){
        Log.i("","Debut du remplissage")
        for (s:String in Utils(context).files){
            getEntitiesFromFile(s,Utils(context).directoryPath)
        }
        nM.cancel(1)
        Log.i("","   ")
    }
    private fun sendProgress(){
        /** Création de la chaine pour la notification **/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,"StarDP",importance)
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        /**Creation de la notification en elle même **/
        val content = ""
        builder.setContentTitle(context.getString(R.string.filling_text))
            .setColor(ContextCompat.getColor(context,R.color.teal_200))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setSilent(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content).setBigContentTitle(context.getString(R.string.filling_text)))
            .setAutoCancel(false)
            .setProgress(100,0,false)
            .setChannelId(CHANNEL_ID)
        nM.notify(1,builder.build())
    }

    private fun extract(targetPath: String, destinationPath: String) {
        Log.i("","extraction lancée")
        //Toast.makeText(context,"extraction des fichiers", Toast.LENGTH_LONG).show()
        try {
            val f: File = File(targetPath)
            if (!f.isDirectory){
                f.mkdirs()
            }
            this.publishProgress(1)
            ZipFile(f).use { it ->
                it.entries()
                    .asSequence()
                    .filter { !it.isDirectory }
                    .forEach { fl ->
                        if ( Utils(context).files.contains(fl.name) ){
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
            Log.i("","Extraction terminée")
        }catch (e: IOException){
            Log.i("",e.printStackTrace().toString())
        }


    }

    private  fun getEntitiesFromFile(fileName: String, location:String) {

        try {
            val location: String = location
            val f = File("$location/$fileName")
            val reader = BufferedReader(FileReader(f))
            if(reader != null){
                var lines: Stream<String> = reader.lines().skip(1)
                val l = lines.toList()
                var count:Int = 0
                when(fileName){
                    Utils(context).files[0] -> {
                        this.publishProgress(1)
                        val entities = ArrayList<BusRoutes>()
                        val bV = BusRouteViewModel(application)
                        bV.deleteAllBusRoutes()
                        for (line:String in l){
                            //this.publishProgress(1)
                            count++
                            val fields = line.split(",").toTypedArray()
                            val b = BusRoutes()
                            b.routeId = Utils.removeQuotes(fields[0])
                            b.color = Utils.removeQuotes(fields[7])
                            b.description = Utils.removeQuotes(fields[4])
                            b.shortName = Utils.removeQuotes(fields[2])
                            b.longName = Utils.removeQuotes(fields[3])
                            b.textColor = Utils.removeQuotes(fields[8])
                            b.type = Utils.removeQuotes(fields[5])
                            entities.add(b)
                            if(entities.size == 1000 || count == l.size-1){
                                val c = entities.toList()
                                Log.i("","Ajout de $count élements de busRoutes")
                               // Toast.makeText(Utils.getActivity(),"Ajout de $count élements de busRoutes",Toast.LENGTH_LONG).show()
                                bV.addAllBusRoute(c)
                                entities.clear()
                                this.publishProgress(1)
                            }
                        }
                    }
                    Utils(context).files[1] -> {
                        //this.publishProgress(1)
                        //calendar
                        val entities = ArrayList<Calendar>()
                        val cV = CalendarViewModel(application)
                        cV.deleteAllCalendar()
                        for (line:String in l){
                            //this.publishProgress(1)
                            count++
                            val fields = line.split(",").toTypedArray()
                            val c = Calendar()
                            c.serviceId = Utils.removeQuotes(fields[0])
                            c.monday = Utils.removeQuotes(fields[1])
                            c.tuesday = Utils.removeQuotes(fields[2])
                            c.wednesday = Utils.removeQuotes(fields[3])
                            c.thursday = Utils.removeQuotes(fields[4])
                            c.friday = Utils.removeQuotes(fields[5])
                            c.saturday = Utils.removeQuotes(fields[6])
                            c.sunday = Utils.removeQuotes(fields[7])
                            c.startDate = Utils.removeQuotes(fields[8])
                            c.endDate = Utils.removeQuotes(fields[9])
                            entities.add(c)
                            if(entities.size == 1000 || count == l.size-1){
                                val cv = entities.toList()
                                Log.i("","Ajout de $count élements de calendar")
                                //Toast.makeText(Utils.getActivity(),"Ajout de $count élements de calendar",Toast.LENGTH_LONG).show()
                                cV.addAllCalendar(cv)
                                entities.clear()
                                this.publishProgress(1)
                            }
                        }
                    }
                    Utils(context).files[2] -> {
                        this.publishProgress(1)
                        //trips
                        val entities = ArrayList<Trips>()
                        val tV = TripsViewModel(application)
                        tV.deleteAllTrips()
                        for (line:String in l){
                            //this.publishProgress(1)
                            count++
                            val fields = line.split(",").toTypedArray()
                            val t = Trips()
                            t.tripId =Utils.removeQuotes(fields[2])
                            t.routeId = Utils.removeQuotes(fields[0])
                            t.serviceId = Utils.removeQuotes(fields[1])
                            t.headSign = Utils.removeQuotes(fields[3])
                            t.directionId = Utils.removeQuotes(fields[5])
                            t.blockId = Utils.removeQuotes(fields[6])
                            t.wheelChairAccessible = Utils.removeQuotes(fields[8])
                            entities.add(t)
                            if(entities.size == 1000 || count == l.size-1){
                                val c = entities.toList()
                                Log.i("","Ajout de $count élements de trips")
                                //Toast.makeText(Utils.getActivity(),"Ajout de $count élements de trips",Toast.LENGTH_LONG).show()
                                tV.addAllTrips(c)
                                entities.clear()
                                this.publishProgress(1)
                            }
                        }
                    }
                    Utils(context).files[3] -> {
                        //stops
                        this.publishProgress(1)
                        val sV = StopsViewModel(application)
                        sV.deleteAllStops()
                        val entities = ArrayList<Stops>()
                        for (line:String in l){
                           // this.publishProgress(1)
                            count++
                            val fields = line.split(",").toTypedArray()
                            val s = Stops()
                            s.stopId = Utils.removeQuotes(fields[0])
                            s.stopName = Utils.removeQuotes(fields[2])
                            s.description = Utils.removeQuotes(fields[3])
                            s.latitude = Utils.removeQuotes(fields[4])
                            s.longitutde = Utils.removeQuotes(fields[5])
                            s.wheelChairBoarding = Utils.removeQuotes(fields[11])
                            entities.add(s)
                            if(entities.size == 1000 || count == l.size-1){
                                val c = entities.toList()
                                Log.i("","Ajout de $count élements de stops")
                                //Toast.makeText(Utils.getActivity(),"Ajout de $count élements de stops",Toast.LENGTH_LONG).show()
                                sV.addAllBStops(c)
                                entities.clear()
                                this.publishProgress(1)
                            }
                        }
                    }
                    Utils(context).files[4] -> {
                        //stops_times
                        //this.publishProgress(1)
                        val stV = StopTimesViewModel(application)
                        stV.deleteAllStopTimes()
                        val entities = ArrayList<StopTimes>()
                        for (line:String in l){
                           // this.publishProgress(1)
                            count++
                            val fields = line.split(",").toTypedArray()
                            val st = StopTimes()
                            st.tripId  = Utils.removeQuotes(fields[0])
                            st.arrivalTime = Utils.removeQuotes(fields[1])
                            st.departureTime = Utils.removeQuotes(fields[2])
                            st.stopId = Utils.removeQuotes(fields[3])
                            st.stopSequence = Utils.removeQuotes(fields[4])
                            entities.add(st)
                            if(entities.size == 1000 || count == l.size-1){
                                val c = entities.toList()
                                Log.i("","Ajout de $count élements de stopsTimes")
                               // Toast.makeText(Utils.getActivity(),"Ajout de $count élements de stops",Toast.LENGTH_LONG).show()
                                stV.addAllStopTimes(c)
                                entities.clear()
                                this.publishProgress(1)
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}