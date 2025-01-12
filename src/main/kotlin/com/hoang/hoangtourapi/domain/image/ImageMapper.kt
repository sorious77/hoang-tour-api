package com.hoang.hoangtourapi.domain.image

import com.hoang.hoangtourapi.domain.image.model.Image
import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(
    componentModel = "spring",
)
interface ImageMapper {
    @Mappings(
        Mapping(target = "insOprt", source = "req.memberId"),
        Mapping(target = "updOprt", source = "req.memberId"),
    )
    fun toEntity(
        reviewId: Long,
        imageUrl: String,
        order: Int,
        req: SaveReviewReq,
    ): Image
}
