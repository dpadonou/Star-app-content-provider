package fr.istic.mob.starapplication.dao

import android.database.Cursor
import androidx.room.*
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.database.StarContract

@Dao
interface CalendarDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalendar(c:Calendar)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCalendar(calendars:List<Calendar>)
    @Update
    suspend fun updateCalendar(c:Calendar)
    @Delete
    suspend fun deleteCalendar(c:Calendar)

    /**
     * select all in the Calendar table
     * @return
     */
    @Query("Select * from " + StarContract.Calendar.CONTENT_PATH)
    fun getCalendarList(): List<Calendar?>?


    @Query("Select * from " + StarContract.Calendar.CONTENT_PATH)
    fun getCalendarListCursor(): Cursor?

    @Query("DELETE FROM " + StarContract.Calendar.CONTENT_PATH)
    fun deleteAll()
   /* @Query("DELETE FROM Calendar")
    suspend fun deleteAllCalendar() */
}