package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.StopTimes
import fr.istic.mob.starapplication.models.Trips

@Dao
interface TripsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrips(t: Trips)
    @Update
    fun updateTrips(t:Trips)
    @Delete
    fun deleteTrips(t:Trips)
    @Query("SELECT * FROM Trips")
    fun getAllTrips(): Cursor
    @Query("SELECT * FROM Trips WHERE id=:id")
    fun getTrips(id:Int)
}