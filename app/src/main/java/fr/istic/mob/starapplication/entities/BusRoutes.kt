package fr.istic.mob.starapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_routes")
class BusRoutes {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    constructor(
        id: Int,
        shortName: String,
        longName: String,
        description: String,
        type: String,
        color: String,
        textColor: String
    ) {
        this.id = id
        this.shortName = shortName
        this.longName = longName
        this.description = description
        this.type = type
        this.color = color
        this.textColor = textColor
    }

    private var shortName: String = ""
    private var longName: String = ""
    private var description: String = ""
    private var type: String = ""
    private var color: String = ""
    private var textColor: String = ""

}