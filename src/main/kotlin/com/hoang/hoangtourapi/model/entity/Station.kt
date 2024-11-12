package com.hoang.hoangtourapi.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "STATIONS")
data class Station(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATION_ID", nullable = false)
    val stationId: Long,
    @Column(name = "STATION_NAME", nullable = false)
    val stationName: String,
    @Column(name = "STATION_ENG_NAME", nullable = false)
    val stationEngName: String,
    @Column(name = "LATITUDE", nullable = false)
    val latitude: Double,
    @Column(name = "LONGITUDE", nullable = false)
    val longitude: Double,
    @Column(name = "DESCRIPTION", nullable = true)
    val description: String
) : BaseEntity()
