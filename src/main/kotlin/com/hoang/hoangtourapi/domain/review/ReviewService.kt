package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.image.ImageService
import com.hoang.hoangtourapi.domain.member.MemberService
import com.hoang.hoangtourapi.domain.member.model.Member
import com.hoang.hoangtourapi.domain.review.model.DeleteReveiwReq
import com.hoang.hoangtourapi.domain.review.model.ReviewDetailRes
import com.hoang.hoangtourapi.domain.review.model.ReviewRes
import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import com.hoang.hoangtourapi.domain.review.model.UpdateReviewReq
import com.hoang.hoangtourapi.domain.station.StationService
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.enums.Status
import com.hoang.hoangtourapi.exception.BaseException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val memberService: MemberService,
    private val stationService: StationService,
    private val reviewMapper: ReviewMapper,
    private val imageService: ImageService,
) {
    @Transactional
    fun saveReview(req: SaveReviewReq): ReviewRes {
        val member = validateSaveParam(req.memberId, req.email, req.stationName, req.stationId)

        val review = reviewRepository.save(reviewMapper.toEntity(req, member.memberId))

        imageService.saveImageList(review.reviewId, req.imageList, req)

        return reviewMapper.toReviewRes(review, member.nickname, req.stationName, req.email)
    }

    fun findReviewList(page: Int): List<ReviewDetailRes> {
        val reviewList = reviewRepository.findReviewListPaging(page)

        return reviewList
    }

    fun findReviewByReviewId(reviewId: Long): ReviewDetailRes? {
        return reviewRepository.findReviewDetailByReviewId(reviewId)
    }

    @Transactional
    fun updateReview(req: UpdateReviewReq): ReviewRes {
        val member = validateSaveParam(req.memberId, req.email, req.stationName, req.stationId)

        val review =
            reviewRepository.findReviewByReviewId(req.reviewId)
                ?: throw BaseException(BaseResponseStatus.ENTITY_NOT_FOUND)

        if (review.insOprt != member.memberId.toString()) throw BaseException(BaseResponseStatus.NO_AUTH)

        if (review.status == Status.DELETE) throw BaseException(BaseResponseStatus.UPDATE_IMPOSSIBLE)

        reviewRepository.save(
            review.apply {
                this.title = req.title
                this.contents = req.contents
                this.stationId = req.stationId
            },
        )

        return reviewMapper.toReviewRes(review, member.nickname, req.stationName, req.email)
    }

    @Transactional
    fun deleteReview(req: DeleteReveiwReq): Boolean {
        val member = memberService.findMemberByMemberId(req.memberId)
        if (member?.email != req.email) throw BaseException(BaseResponseStatus.INVALID_MEMBER)

        val review =
            reviewRepository.findReviewByReviewId(req.reviewId)
                ?: throw BaseException(BaseResponseStatus.ENTITY_NOT_FOUND)

        if (review.insOprt != member.memberId.toString()) throw BaseException(BaseResponseStatus.NO_AUTH)

        if (review.status == Status.DELETE) throw BaseException(BaseResponseStatus.UPDATE_IMPOSSIBLE)

        reviewRepository.save(
            review.apply {
                status = Status.DELETE
            },
        )

        return true
    }

    private fun validateSaveParam(
        memberId: Long,
        email: String,
        stationName: String,
        stationId: Long,
    ): Member {
        val member = memberService.findMemberByMemberId(memberId)
        if (member?.email != email) throw BaseException(BaseResponseStatus.INVALID_MEMBER)

        val station = stationService.findStationByStationId(stationId)
        if (station?.stationName != stationName) throw BaseException(BaseResponseStatus.ENTITY_NOT_FOUND)

        return member
    }
}
