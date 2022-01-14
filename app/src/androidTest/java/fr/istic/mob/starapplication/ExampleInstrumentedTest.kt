package fr.istic.mob.starapplication

import androidx.room.Query
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.istic.mob.starapplication.database.StarContract
import fr.istic.mob.starapplication.database.StarDatabase

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val b = StarDatabase.getDatabase(appContext).stopsDAO()
        val qry = SimpleSQLiteQuery( "SELECT DISTINCT *" +
                "                 FROM stop,stoptime,trip" +
                "                 WHERE trip.trip_id = stoptime.trip_id" +
                "                 AND stop.stop_id = stoptime.stop_id" +
                "                 AND trip.route_id = '0005'" +
                "                 AND trip.direction_id = '1'" +
                "                 ORDER BY stoptime.departure_time")
        val qry2 = SimpleSQLiteQuery( "SELECT DISTINCT stop.id,stop.stop_desc,stop.stop_id,stop.stop_lat,"
                +"stop.stop_lon,stop.stop_name,"
                +"trip.trip_headsign,trip.direction_id,trip.service_id" +
                "                 FROM stop,stoptime,trip" +
                "                 WHERE trip.trip_id = stoptime.trip_id" +
                "                 AND stop.stop_id = stoptime.stop_id" +
                "                 AND trip.route_id = '0005'" +
                "                 AND trip.direction_id = '1'" +
                "                 ORDER BY stoptime.departure_time")

        val c = b.getStopsByLines(qry2)
        //val c = b.getStopList()
        assertNotEquals(0,c?.count)
    }
}