package fr.istic.mob.starapplication.viewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.istic.mob.starapplication.database.StarDatabase
import fr.istic.mob.starapplication.models.Trips
import fr.istic.mob.starapplication.repositories.TripsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripsViewModel(application: Application):AndroidViewModel(application) {
    private val allTrips: Cursor
    private val repository: TripsRepository

    init {
        val tripsDAO = StarDatabase.getDatabase(application).tripsDAO()
        repository = TripsRepository(tripsDAO)
        allTrips = repository.allTrips
    }

    fun addTrips(t: Trips){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrips(t)
        }

    }
}