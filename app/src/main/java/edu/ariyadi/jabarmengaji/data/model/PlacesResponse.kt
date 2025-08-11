package edu.ariyadi.jabarmengaji.data.model

data class PlacesResponse(
    val results: List<Place>
)

data class Place(
    val name: String,
    val geometry: Geometry,
    val vicinity: String
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)