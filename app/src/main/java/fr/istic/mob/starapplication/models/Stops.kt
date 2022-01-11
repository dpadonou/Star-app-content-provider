package fr.istic.mob.starapplication.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.istic.mob.starapplication.database.StarContract

@Entity(tableName = StarContract.Stops.CONTENT_PATH)
class Stops {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id:Int = 0
    @ColumnInfo(name="stop_id")
    var stopId:String = ""
    @ColumnInfo(name = "stop_name")
    var stopName:String = ""
    @ColumnInfo(name="stop_desc")
    var description:String = ""
    @ColumnInfo(name = "stop_lat")
    var latitude:String = ""
    @ColumnInfo(name="stop_lon")
    var longitutde:String = ""
    @ColumnInfo(name="wheelchair_boarding")
    var wheelChairBoarding:String = ""

    constructor(
        stopName: String,
        description: String,
        latitude: String,
        wheelChairBoarding: String,
        longitutde: String
    ) {
        this.stopName = stopName
        this.description = description
        this.latitude = latitude
        this.longitutde = longitutde
        this.wheelChairBoarding = wheelChairBoarding
    }

    constructor()
}