package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.review.model.Review
import com.hoang.hoangtourapi.domain.review.model.ReviewDetailRes
import com.hoang.hoangtourapi.domain.review.model.ReviewImage
import com.hoang.hoangtourapi.domain.review.model.ReviewRes
import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.NullValueMappingStrategy

@Mapper(
    componentModel = "spring",
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
)
interface ReviewMapper {
    @Mappings(
        Mapping(target = "insOprt", source = "memberId"),
        Mapping(target = "updOprt", source = "memberId"),
    )
    fun toEntity(
        req: SaveReviewReq,
        memberId: Long,
    ): Review

    fun toReviewRes(
        review: Review,
        nickname: String,
        stationName: String,
        email: String,
        isModified: Boolean? = false,
    ): ReviewRes

    @Mapping(target = "userEmail", source = "review.email")
    fun toReviewDetailRes(
        review: ReviewRes,
        reviewImageList: List<ReviewImage>,
    ): ReviewDetailRes
}
