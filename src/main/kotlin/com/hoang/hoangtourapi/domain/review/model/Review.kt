package com.hoang.hoangtourapi.domain.review.model

import com.hoang.hoangtourapi.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "REVIEWS")
class Review(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID", nullable = false)
    var reviewId: Long,
    @Column(name = "TITLE", nullable = false)
    var title: String,
    @Column(name = "CONTENTS", nullable = false)
    var contents: String,
    @Column(name = "STATION_ID", nullable = false)
    var stationId: Long,
    @Column(name = "MEMBER_ID", nullable = false)
    var memberId: Long,
) : BaseEntity()
