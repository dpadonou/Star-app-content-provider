package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.models.StopTimes

@Dao
interface StopsTimesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStopTimes(st: StopTimes)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllStopsTimes(stopsTimes:ArrayList<StopTimes>)
    @Update
    suspend fun updateStopTimes(st: StopTimes)
    @Delete
    suspend fun deleteStopTimes(st: StopTimes)
    @Query("DELETE FROM StopTimes")
    suspend fun deleteAllStopsTimes()
}