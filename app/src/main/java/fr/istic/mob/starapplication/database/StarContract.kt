package fr.istic.mob.starapplication.database

import android.net.Uri
import android.provider.BaseColumns

interface StarContract {
    companion object{
        val AUTHORITY: String = "fr.istic.mob.starapplication"

        val AUTHORITY_URI: Uri = Uri.parse("content://$AUTHORITY")
    }


     interface BusRoutes {
        interface BusRouteColumns : BaseColumns {
            companion object {
                const val _ID = "_id"
                const val ROUTE_ID = "route_id"
                const val SHORT_NAME = "route_short_name"
                const val LONG_NAME = "route_long_name"
                const val DESCRIPTION = "route_desc"
                const val TYPE = "route_type"
                const val COLOR = "route_color"
                const val TEXT_COLOR = "route_text_color"
            }
        }

        companion object {
            const val CONTENT_PATH = "busroute"
            val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)
            const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fr.istic.mob.starapplication.busroute"
            const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.fr.istic.mob.starapplication.busroute"
        }
    }

    interface Trips {
        interface TripColumns : BaseColumns {
            companion object {
                const val _ID = "id"
                const val TRIP_ID = "trip_id"
                const val ROUTE_ID = "route_id"
                const val SERVICE_ID = "service_id"
                const val HEADSIGN = "trip_headsign"
                const val DIRECTION_ID = "direction_id"
                const val BLOCK_ID = "block_id"
                const val WHEELCHAIR_ACCESSIBLE = "wheelchair_accessible"
            }
        }

        companion object {
            const val CONTENT_PATH = "trip"
            val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)
            const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fr.istic.mob.stareg.trip"
            const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.fr.istic.mob.stareg.trip"
        }
    }

     interface Stops {
        interface StopColumns : BaseColumns {
            companion object {
                const val _ID = "id"
                const val STOP_ID = "stop_id"
                const val NAME = "stop_name"
                const val DESCRIPTION = "stop_desc"
                const val LATITUDE = "stop_lat"
                const val LONGITUDE = "stop_lon"
                const val WHEELCHAIR_BOARDING = "wheelchair_boarding"
            }
        }

        companion object {
            const val CONTENT_PATH = "stop"
            val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)
            const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fr.istic.mob.starapplication.stop"
            const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.fr.istic.mob.starapplication.stop"
        }
    }

     interface StopTimes {
        interface StopTimeColumns : BaseColumns {
            companion object {
                const val _ID = "id"
                const val TRIP_ID = "trip_id"
                const val ARRIVAL_TIME = "arrival_time"
                const val DEPARTURE_TIME = "departure_time"
                const val STOP_ID = "stop_id"
                const val STOP_SEQUENCE = "stop_sequence"
            }
        }

        companion object {
            const val CONTENT_PATH = "stoptime"
            val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)

            // select stop_time.*, trip.*, calendar.*
            const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fr.istic.mob.starapplication.stoptime"
            const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.fr.istic.mob.starapplication.stoptime"
        }
    }

     interface Calendar {
        interface CalendarColumns : BaseColumns {
            companion object {
                const val _ID = "id"
                const val SERVICE_ID = "service_id"
                const val MONDAY = "monday"
                const val TUESDAY = "tuesday"
                const val WEDNESDAY = "wednesday"
                const val THURSDAY = "thursday"
                const val FRIDAY = "friday"
                const val SATURDAY = "saturday"
                const val SUNDAY = "sunday"
                const val START_DATE = "start_date"
                const val END_DATE = "end_date"
            }
        }

        companion object {
            const val CONTENT_PATH = "calendar"
            val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)
            const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fr.istic.mob.starapplication.calendar"
            const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.fr.istic.mob.starapplication.calendar"
        }
    }

     interface RouteDetails {
        companion object {
            const val CONTENT_PATH = "routedetail"
            val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)
            const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fr.istic.mob.stareg.routedetail"
            const val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.fr.istic.mob.stareg.routedetail"
        }
    }


     interface SearchedStops {
        companion object {
            const val CONTENT_PATH = "searchedstop"
            val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)
            const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fr.istic.mob.starapplication.searchedstops"
            const val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.fr.istic.mob.stareg.searchedstops"
        }
    }

     interface RoutesForStop {
        companion object {
            const val CONTENT_PATH = "routesForStop"
            val CONTENT_URI: Uri = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH)
            // select stop.stop_name, stop_time.arrival_time
            const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fr.istic.mob.starapplication.routesForStop"
            const val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.fr.istic.mob.stareg.routesForStop"
        }
    }

}