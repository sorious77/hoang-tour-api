package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.image.ImageS3Service
import com.hoang.hoangtourapi.domain.image.ImageService
import com.hoang.hoangtourapi.domain.member.MemberService
import com.hoang.hoangtourapi.domain.review.model.ReviewDetailRes
import com.hoang.hoangtourapi.domain.review.model.ReviewRes
import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import com.hoang.hoangtourapi.domain.station.StationService
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.exception.BaseException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val memberService: MemberService,
    private val stationService: StationService,
    private val reviewMapper: ReviewMapper,
    private val imageS3Service: ImageS3Service,
    private val imageService: ImageService,
) {
    @Transactional
    fun saveReview(req: SaveReviewReq): ReviewRes {
        val member = memberService.findMemberByMemberId(req.memberId)
        if (member?.email != req.email) throw BaseException(BaseResponseStatus.INVALID_MEMBER)

        val station = stationService.findStationByStationId(req.stationId)
        if (station?.stationName != req.stationName) throw BaseException(BaseResponseStatus.ENTITY_NOT_FOUND)

        val review = reviewRepository.save(reviewMapper.toEntity(req, member.memberId))

        imageS3Service.uploadMultiImage(req.imageList)
        val imageUrlList =
            req.imageList.mapIndexed { index, file ->
                val url = imageS3Service.getFile(file.originalFilename ?: "")

                Pair(url, index)
            }

        imageService.saveImageList(review.reviewId, imageUrlList, req)

        return reviewMapper.toReviewRes(review, member.nickname, req.stationName)
    }

    fun findReviewList(page: Int): List<ReviewDetailRes> {
        val reviewList = reviewRepository.findReviewListPaging(page)

        return reviewList
    }

    fun findReviewByReviewId(reviewId: Long): ReviewDetailRes? {
        return reviewRepository.findReviewByReviewId(reviewId)
    }
}
