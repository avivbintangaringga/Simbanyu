package com.avivba.simbanyu.ui.shared.model

data class Coordinate(
    val latitude: Double,
    val longitude: Double,
) {
    companion object {
        fun fromDomain(coordinate: com.avivba.simbanyu.domain.model.Coordinate): Coordinate {
            return Coordinate(
                latitude = coordinate.latitude,
                longitude = coordinate.longitude,
            )
        }
    }
}