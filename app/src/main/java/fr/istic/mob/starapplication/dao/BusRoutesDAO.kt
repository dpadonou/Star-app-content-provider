package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.BusRoutes

@Dao
interface BusRoutesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBusRoutes(b:BusRoutes)
    @Insert
    suspend fun insertAllBusRoutes(l:ArrayList<BusRoutes>)
    @Update
    suspend fun updateBusRoutes(b:BusRoutes)
    @Delete
    suspend fun deleteBusRoutes(b:BusRoutes)
    @Query("DELETE FROM BusRoutes")
    suspend fun deleteAllBusRoutes()

    @Query("SELECT * FROM BusRoutes")
    suspend fun getBusRoutes(): List<BusRoutes>
}