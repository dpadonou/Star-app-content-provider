package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.Calendar


@Dao
interface CalendarDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalendar(c:Calendar)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCalendar(calendars:ArrayList<Calendar>)
    @Update
    fun updateCalendar(c:Calendar)
    @Delete
    fun deleteCalendar(c:Calendar)
    @Query("DELETE FROM Calendar")
    fun deleteAllCalendar()
    @Query("SELECT * FROM Calendar")
    fun getAllCalendar(): Cursor
    @Query("SELECT * FROM Calendar WHERE id=:id")
    fun getCalendar(id:Int)
}