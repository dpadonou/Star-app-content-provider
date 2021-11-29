package fr.istic.mob.starapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import fr.istic.mob.starapplication.database.BusRoutesDatabase
import fr.istic.mob.starapplication.entities.BusRoutes
import fr.istic.mob.starapplication.repository.BusRoutesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusRoutesViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllBusRoutes: LiveData<List<BusRoutes>>
    private val repository : BusRoutesRepository

    init {
        val busRoutesDao = BusRoutesDatabase.getDatabase(application).busRoutesDao()
        repository = BusRoutesRepository(busRoutesDao)
        readAllBusRoutes = repository.getAllBusRoutes
    }

    fun createBusRoutes(busRoutes: BusRoutes){
        viewModelScope.launch(Dispatchers.IO){
            repository.createBusRoutes(busRoutes)
        }
    }

}