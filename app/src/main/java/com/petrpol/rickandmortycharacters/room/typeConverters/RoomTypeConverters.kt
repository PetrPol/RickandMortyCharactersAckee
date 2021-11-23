package com.petrpol.rickandmortycharacters.room.typeConverters

import androidx.room.TypeConverter
import com.petrpol.rickandmortycharacters.model.Location

class RoomTypeConverters {
        @TypeConverter
        fun fromTimestamp(value: String?): Location? {
            if (value == null)
                return null

            val valueSplit = value.split(';')

            return Location(valueSplit[0],valueSplit[1])
        }

        @TypeConverter
        fun dateToTimestamp(location: Location?): String? {
            if (location == null)
                return null

            return location.name + ";" + location.url
        }
}