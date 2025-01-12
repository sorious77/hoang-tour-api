package com.hoang.hoangtourapi.domain.image

import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import org.springframework.stereotype.Service

@Service
class ImageService(
    private val imageMapper: ImageMapper,
    private val imageRepository: ImageRepository,
) {
    fun saveImageList(
        reviewId: Long,
        imageList: List<Pair<String, Int>>,
        req: SaveReviewReq,
    ) {
        imageList.forEach {
            val (url, order) = it

//            println(imageMapper.toEntity(reviewId, url, order, req))

            imageRepository.save(
                imageMapper.toEntity(reviewId, url.removePrefix("https://"), order, req),
            )
        }
    }
}
