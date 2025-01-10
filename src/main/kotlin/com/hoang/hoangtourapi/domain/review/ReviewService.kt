package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.member.MemberService
import com.hoang.hoangtourapi.domain.review.model.ReviewDetailRes
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
) {
    @Transactional
    fun saveReview(req: SaveReviewReq): Any {
        val member = memberService.findMemberByMemberId(req.memberId)
        if (member?.email != req.email) throw BaseException(BaseResponseStatus.INVALID_MEMBER)

        val station = stationService.findStationByStationId(req.stationId)
        if (station?.stationName != req.stationName) throw BaseException(BaseResponseStatus.ENTITY_NOT_FOUND)

        val review = reviewRepository.save(reviewMapper.toEntity(req, member.memberId))

        return reviewMapper.toReviewRes(review, member.nickname, req.stationName)
    }

    fun getReviewList(page: Int): List<ReviewDetailRes> {
        val reviewList = reviewRepository.findReviewListPaging(page)

        return reviewList
    }
}
