package fr.istic.mob.starapplication.viewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.istic.mob.starapplication.database.StarDatabase
import fr.istic.mob.starapplication.models.BusRoutes
import fr.istic.mob.starapplication.models.Trips
import fr.istic.mob.starapplication.repositories.TripsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripsViewModel(application: Application):AndroidViewModel(application) {
    private val repository: TripsRepository

    init {
        val tripsDAO = StarDatabase.getDatabase(application).tripsDAO()
        repository = TripsRepository(tripsDAO)
    }

    fun addTrips(t: Trips){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrips(t)
        }

    }
     fun addAllTrips(trips: List<Trips>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAllTrips(trips)
        }
    }
    fun deleteAllTrips(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTrips()
        }
    }
}