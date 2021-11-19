package fr.istic.mob.starapplication.viewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.istic.mob.starapplication.database.StarDatabase
import fr.istic.mob.starapplication.models.Calendar
import fr.istic.mob.starapplication.repositories.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(application: Application):AndroidViewModel(application) {
    private val allCalendar: Cursor
    private val repository: CalendarRepository

    init {
        val calendarDAO = StarDatabase.getDatabase(application).calendarDAO()
        repository = CalendarRepository(calendarDAO)
        allCalendar = repository.allCalendar
    }

    fun addCalendar(c:Calendar){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCalendar(c)
        }

    }
}