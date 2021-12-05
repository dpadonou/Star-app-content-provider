package fr.istic.mob.starapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.istic.mob.starapplication.models.BusRoutes

@Dao
interface BusRoutesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBusRoutes(b: BusRoutes)

    @Insert
    suspend fun insertAllBusRoutes(l:List<BusRoutes>)

    @Update
    fun updateBusRoutes(b: BusRoutes)

    @Delete
    fun deleteBusRoutes(b: BusRoutes)

    @Query("DELETE FROM BusRoutes")
    fun deleteAllBusRoutes()

   /* @Query("SELECT * FROM BusRoutes")
    fun getAllBusRoutes(): Cursor*/

    /*@Query("SELECT * FROM BusRoutes WHERE _id=:id")
    fun getBusRoutes(id: Int)*/
}