package fr.istic.mob.starapplication.viewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.istic.mob.starapplication.database.StarDatabase
import fr.istic.mob.starapplication.models.BusRoutes
import fr.istic.mob.starapplication.repositories.BusRouteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusRouteViewModel(application: Application): AndroidViewModel(application) {
    private val allBusRoutes:Cursor
    private val repository: BusRouteRepository

    init {
        val busRouteDAO = StarDatabase.getDatabase(application).busRoutesDAO()
        repository = BusRouteRepository(busRouteDAO)
        allBusRoutes = repository.allBusRoutes
    }

    fun addBusRoute(busRoutes: BusRoutes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBusRoute(busRoutes)
        }
    }
    fun addAllBusRoute(busRoutes: ArrayList<BusRoutes>){
            viewModelScope.launch(Dispatchers.IO) {
                repository.addAllBusRoutes(busRoutes)
            }

    }
}