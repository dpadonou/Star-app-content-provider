package fr.istic.mob.starapplication.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.istic.mob.starapplication.database.StarContract

@Entity(tableName = StarContract.BusRoutes.CONTENT_PATH)
class BusRoutes {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private var _id: Int = 0
    @ColumnInfo(name="route_id")
    var routeId:String = ""

    @ColumnInfo(name = "route_short_name")
    var shortName: String = ""

    @ColumnInfo(name = "route_long_name")
    var longName: String = ""

    @ColumnInfo(name = "route_desc")
    var description: String = ""

    @ColumnInfo(name = "route_type")
    var type: String = ""

    @ColumnInfo(name = "route_color")
    var color: String = ""

    @ColumnInfo(name = "route_text_color")
    var textColor: String = ""

    /** Getters and setters for _id **/
    fun getId(): Int {
        return this._id
    }

    fun setId(_id: Int) {
        this._id = _id
    }

    override fun toString(): String {
        return "BusRoutes(_id=$_id, shortName='$shortName', longName='$longName', description='$description', type='$type', color='$color', textColor='$textColor')"
    }


}