package com.avivba.simbanyu.ui.shared.model

data class UiCoordinate(
    val latitude: Double,
    val longitude: Double,
) {
    fun toDomain(): com.avivba.simbanyu.domain.model.Coordinate {
        return com.avivba.simbanyu.domain.model.Coordinate(
            latitude = latitude,
            longitude = longitude,
        )
    }

    companion object {
        fun fromDomain(coordinate: com.avivba.simbanyu.domain.model.Coordinate): UiCoordinate {
            return UiCoordinate(
                latitude = coordinate.latitude,
                longitude = coordinate.longitude,
            )
        }
    }
}