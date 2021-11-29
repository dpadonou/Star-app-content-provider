package fr.istic.mob.starapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_routes")
class BusRoutes {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    private var shortName: String = ""
    private var longName: String = ""
    private var description: String = ""
    private var type: String = ""
    private var color: String = ""
    private var textColor: String = ""

}