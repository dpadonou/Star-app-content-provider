package fr.istic.mob.starapplication.repositories

import fr.istic.mob.starapplication.dao.CalendarDAO
import fr.istic.mob.starapplication.models.Calendar

class CalendarRepository(private val calendarDAO:CalendarDAO) {
    fun addCalendar(c:Calendar){
        calendarDAO.insertCalendar(c)
    }
    fun addAllCalendar(calendars:ArrayList<Calendar>){
        calendarDAO.insertAllCalendar(calendars)
    }
    fun deleteAllCalendar(){
        calendarDAO.deleteAllCalendar()
    }
}