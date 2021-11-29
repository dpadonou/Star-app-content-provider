package fr.istic.mob.starapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.istic.mob.starapplication.dao.BusRoutesDao
import fr.istic.mob.starapplication.entities.BusRoutes

@Database(entities = [BusRoutes::class], version = 1, exportSchema = false)
abstract class BusRoutesDatabase : RoomDatabase() {

    abstract fun busRoutesDao() : BusRoutesDao

    companion object{
        @Volatile
        private var INSTANCE: BusRoutesDatabase? = null

        fun getDatabase(context: Context) : BusRoutesDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BusRoutesDatabase::class.java,
                    "star_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}