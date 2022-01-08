package fr.istic.mob.starapplication.viewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.istic.mob.starapplication.database.StarDatabase
import fr.istic.mob.starapplication.models.BusRoutes
import fr.istic.mob.starapplication.models.Stops
import fr.istic.mob.starapplication.repositories.StopsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StopsViewModel(application: Application):AndroidViewModel(application) {
    private val repository: StopsRepository

    init {
        val stopDAO = StarDatabase.getDatabase(application).stopsDAO()
        repository = StopsRepository(stopDAO)
    }

    fun addStops(s: Stops){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStops(s)
        }
    }
     fun addAllBStops(stops: List<Stops>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAllStops(stops)
        }
    }
    fun deleteAllStops(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllStops()
        }
    }
}