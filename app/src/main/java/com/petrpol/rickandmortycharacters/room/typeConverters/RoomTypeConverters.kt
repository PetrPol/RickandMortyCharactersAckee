package com.petrpol.rickandmortycharacters.room.typeConverters

import androidx.room.TypeConverter
import com.petrpol.rickandmortycharacters.model.Location

/** Type converters for room DB */
class RoomTypeConverters {

    /** Converts location object to serializable string */
    @TypeConverter
    fun fromTimestamp(value: String?): Location? {
        if (value == null)
            return null

        val valueSplit = value.split(';')

        return Location(valueSplit[0], valueSplit[1])
    }

    /** Converts serializable string to location object */
    @TypeConverter
    fun dateToTimestamp(location: Location?): String? {
        if (location == null)
            return null

        return location.name + ";" + location.url
    }
}