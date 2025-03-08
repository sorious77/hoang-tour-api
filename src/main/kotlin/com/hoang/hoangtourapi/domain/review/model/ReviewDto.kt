package com.hoang.hoangtourapi.domain.review.model

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class SaveReviewReq(
    val title: String,
    val contents: String,
    val email: String,
    val memberId: Long,
    val stationId: Long,
    val stationName: String,
    val imageList: List<MultipartFile>,
)

data class UpdateReviewReq(
    val reviewId: Long,
    val title: String,
    val contents: String,
    val email: String,
    val memberId: Long,
    val stationId: Long,
    val stationName: String,
)

data class DeleteReveiwReq(
    val reviewId: Long,
    val email: String,
    val memberId: Long,
)

data class ReviewRes(
    val reviewId: Long,
    val title: String,
    val contents: String,
    val nickname: String,
    val email: String,
    val insDate: LocalDateTime,
    val isModified: Boolean,
    val stationName: String,
)

data class ReviewDetailRes(
    val reviewId: Long,
    val title: String,
    val contents: String,
    val nickname: String,
    val userEmail: String,
    val insDate: LocalDateTime,
    val isModified: Boolean,
    val stationName: String,
    val reviewImageList: List<ReviewImage>,
)

data class ReviewImage(
    val reviewId: Long,
    val imageUrl: String,
    val order: Int,
)
