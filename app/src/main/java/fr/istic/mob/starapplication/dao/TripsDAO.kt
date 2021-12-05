package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.models.StopTimes
import fr.istic.mob.starapplication.models.Trips

@Dao
interface TripsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrips(t: Trips)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllTrips(trips:List<Trips>)
    @Update
    suspend fun updateTrips(t:Trips)
    @Delete
    suspend fun deleteTrips(t:Trips)
   /* @Query("DELETE FROM Trips")
    suspend fun deleteAllTrips()*/
}