package fr.istic.mob.starapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.istic.mob.starapplication.dao.*
import fr.istic.mob.starapplication.models.*

@Database(entities = [BusRoutes::class, Calendar::class, Stops::class, StopTimes::class, Trips::class], version = 1, exportSchema = false)
abstract class StarDatabase: RoomDatabase() {
    abstract fun busRoutesDAO():BusRouteDAO
    abstract fun calendarDAO():CalendarDAO
    abstract fun stopsDAO():StopsDAO
    abstract fun stopTimesDAO():StopsTimesDAO
    abstract fun tripsDAO():TripsDAO
    companion object {
        const val name = "star_database"
        @Volatile
        private var INSTANCE: StarDatabase? = null
        fun getDatabase(context: Context): StarDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StarDatabase::class.java,
                    name
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}