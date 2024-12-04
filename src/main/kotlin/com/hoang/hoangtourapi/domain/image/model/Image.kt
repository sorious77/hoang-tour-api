package com.hoang.hoangtourapi.domain.image.model

import com.hoang.hoangtourapi.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "IMAGES")
class Image(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID", nullable = false, unique = true)
    val imageId: Long,
    @Column(name = "REVIEW_ID", nullable = false)
    val reviewId: Long,
    @Column(name = "IMAGE_URL", nullable = false)
    val imageUrl: String,
    @Column(name = "ORDER", nullable = false)
    val order: Int,
) : BaseEntity()
