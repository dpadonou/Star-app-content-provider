package fr.istic.mob.starapplication

import android.app.Application
import android.app.Dialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.HandlerThread
import android.os.IBinder
import android.os.Process
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import fr.istic.mob.starapplication.models.*
import fr.istic.mob.starapplication.viewModel.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.stream.Stream
import kotlin.streams.toList

@RequiresApi(Build.VERSION_CODES.N)
class FillDatabase(): Service() {
    val progression:Progression = Progression(applicationContext,"File Extracting",100)


    init {

    }
     fun fillDatabase(){
        Log.i("","test")
        for (s:String in Utils(applicationContext).files){
            progression.builder.setProgress(1,1,true)
            getEntitiesFromFile(s,Utils(applicationContext).directoryPath)
            if (s == Utils(applicationContext).files[Utils(applicationContext).files.size-1]){
                progression.nM.cancel(1)
                Toast.makeText(applicationContext,"Fin du remplissage de la base",Toast.LENGTH_LONG).show()
            }
        }
         progression.nM.notify(1,progression.builder.build())
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
                    Utils(applicationContext).files[0] -> {
                        val entities = ArrayList<BusRoutes>()
                        val bV = BusRouteViewModel(application)
                        //bV.deleteAllBusRoutes()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val b = BusRoutes()
                            b.color = fields[7]
                            b.description = fields[4]
                            b.shortName = fields[2]
                            b.longName = fields[3]
                            b.textColor = fields[8]
                            b.type = fields[5]
                            entities.add(b)
                            if(entities.size == 2000 || count == l.size-1){
                                val c = entities.toList()
                                Log.i("","Ajout de moins ou de plus 2000 élements de busRoutes")
                                bV.addAllBusRoute(c)
                                entities.clear()
                            }
                        }
                    }
                    Utils(applicationContext).files[1] -> {
                        //calendar
                        val entities = ArrayList<Calendar>()
                        val cV = CalendarViewModel(application)
                        //cV.deleteAllCalendar()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val c = Calendar()
                            c.monday = fields[1]
                            c.tuesday = fields[2]
                            c.wednesday = fields[3]
                            c.thursday = fields[4]
                            c.friday = fields[5]
                            c.saturday = fields[6]
                            c.sunday = fields[7]
                            c.startDate = fields[8]
                            c.endDate = fields[9]
                            entities.add(c)
                            if(entities.size == 2000 || count == l.size-1){
                                val cv = entities.toList()
                                Log.i("","Ajout de moins ou de plus 2000 élements de calendar")
                                cV.addAllCalendar(cv)
                                entities.clear()
                            }
                        }
                    }
                    Utils(applicationContext).files[2] -> {
                        //trips
                        val entities = ArrayList<Trips>()
                        val tV = TripsViewModel(application)
                        //tV.deleteAllTrips()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val t = Trips()
                            t.routeId = fields[0]
                            t.serviceId = fields[1]
                            t.headSign = fields[3]
                            t.directionId = fields[5]
                            t.blockId = fields[6]
                            t.wheelChairAccessible = fields[8]
                            entities.add(t)
                            if(entities.size == 3000 || count == l.size-1){
                                val c = entities.toList()
                                Log.i("","Ajout de moins ou de plus 3000 élements de trips")
                                tV.addAllTrips(c)
                                entities.clear()
                            }
                        }
                    }
                    Utils(applicationContext).files[3] -> {
                        //stops
                        val sV = StopsViewModel(application)
                        //sV.deleteAllStops()
                        val entities = ArrayList<Stops>()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val s = Stops()
                            s.stopName = fields[2]
                            s.description = fields[3]
                            s.latitude = fields[4]
                            s.longitutde = fields[5]
                            s.wheelChairBoarding = fields[11]
                            entities.add(s)
                            if(entities.size == 2000 || count == l.size-1){
                                val c = entities.toList()
                                Log.i("","Ajout de moins ou de plus 2000 élements de stops")
                                sV.addAllBStops(c)
                                entities.clear()
                            }
                        }
                    }
                    Utils(applicationContext).files[4] -> {
                        //stops_times
                        val stV = StopTimesViewModel(application)
                        //stV.deleteAllStopTimes()
                        val entities = ArrayList<StopTimes>()
                        for (line:String in l){
                            count++
                            val fields = line.split(",").toTypedArray()
                            val st = StopTimes()
                            st.tripId  = fields[0]
                            st.arrivalTime = fields[1]
                            st.departureTime = fields[2]
                            st.stopId = fields[3]
                            st.stopSequence = fields[4]
                            entities.add(st)
                            if(entities.size == 10000 || count == l.size-1){
                                val c = entities.toList()
                                Log.i("","Ajout de moins ou de plus 10000 élements de stopsTimes")
                                stV.addAllStopTimes(c)
                                entities.clear()
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
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
        fillDatabase()
        return START_STICKY
    }
}