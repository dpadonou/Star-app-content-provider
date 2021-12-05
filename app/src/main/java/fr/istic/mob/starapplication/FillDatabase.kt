package fr.istic.mob.starapplication

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Build
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
class FillDatabase(var context: Context, var application: Application) {

    var alertDialog = Dialog(context)
    private lateinit var input: ProgressBar
    private lateinit var textView: TextView
    private lateinit var textView2: TextView


    init {
        alertDialog.setCancelable(false)
        alertDialog.setTitle("Fill database")
        alertDialog.setContentView(R.layout.progression)
        input = alertDialog.findViewById<ProgressBar>(R.id.progressBar)
        textView = alertDialog.findViewById<TextView>(R.id.textView)
        textView2 = alertDialog.findViewById<TextView>(R.id.textView2)
        input.isIndeterminate = true
    }
     fun fillDatabase(){
        Log.i("","test")
         textView2.text = "Sauvegarde des données"
        for (s:String in Utils(context).files){
            textView.textSize = 18F
            textView.text = "Ajout des élements de $s."
            getEntitiesFromFile(s,Utils(context).directoryPath)
            if (s == Utils(context).files[Utils(context).files.size-1]){
                alertDialog.dismiss()
                Toast.makeText(context,"Fin du remplissage de la base",Toast.LENGTH_LONG).show()
            }
        }
         alertDialog.show()
    }

    private  fun getEntitiesFromFile(fileName: String, location:String) {

        try {
            val location: String = Utils(context).directoryPath
            val f = File("$location/$fileName")
            val reader = BufferedReader(FileReader(f))
            if(reader != null){
                var lines: Stream<String> = reader.lines().skip(1)
                val l = lines.toList()
                var count:Int = 0
                input.max = 1
                input.progress = 1
                when(fileName){
                    Utils(context).files[0] -> {
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
                    Utils(context).files[1] -> {
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
                    Utils(context).files[2] -> {
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
                    Utils(context).files[3] -> {
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
                    Utils(context).files[4] -> {
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
            alertDialog.dismiss()
        }
    }
}