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
): BaseEntity()

@Entity(name = "STATION_LINES")
data class StationLine(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATION_LINE_ID", nullable = false)
    var stationLineId: Long? = 0L,
    @Column(name = "STATION_ID", nullable = false)
    var stationId: Long,
    @Column(name = "LINE_ID", nullable = false)
    var lineId: Long
)

@Entity(name = "LINES")
data class Line(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_ID", nullable = false)
    var lineId: Long? = 0L,
    @Column(name = "LINE_NAME", nullable = false)
    var lineName: String,
    @Column(name = "LINE_ENG_NAME", nullable = false)
    var lineEngName: String,
) : BaseEntity()