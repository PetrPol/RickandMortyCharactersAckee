package com.petrpol.rickandmortycharacters.room.typeConverters

import com.google.common.truth.Truth.assertThat
import com.petrpol.rickandmortycharacters.model.Location
import org.junit.Test

/** Test for class convertors */
class RoomTypeConvertersTest {

    @Test
    fun locationConvert() {
        val location = Location("nameTest", "www.Location/test/uri")
        val locationInString = RoomTypeConverters().locationToString(location)
        val result = RoomTypeConverters().stringToLocation(locationInString)

        assertThat(result).isNotNull()
        assertThat(result?.name).isEqualTo(location.name)
        assertThat(result?.url).isEqualTo(location.url)
    }
}