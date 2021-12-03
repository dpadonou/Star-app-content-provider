package fr.istic.mob.starapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.istic.mob.starapplication.entities.BusRoutes

@Dao
interface BusRoutesDao {

     @Insert(onConflict = OnConflictStrategy.IGNORE)
     suspend fun addBusRoutes(busRoutes: BusRoutes)

     @Query("SELECT * FROM bus_routes ORDER BY id ASC")
     fun getAllBusRoutes() : LiveData<List<BusRoutes>>

}