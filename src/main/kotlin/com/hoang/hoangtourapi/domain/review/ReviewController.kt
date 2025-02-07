package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.review.model.DeleteReveiwReq
import com.hoang.hoangtourapi.domain.review.model.ReviewDetailRes
import com.hoang.hoangtourapi.domain.review.model.ReviewRes
import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import com.hoang.hoangtourapi.domain.review.model.UpdateReviewReq
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.exception.BaseException
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reviews")
class ReviewController(
    private val reviewService: ReviewService,
) {
    @PostMapping
    fun saveReview(
        @ModelAttribute req: SaveReviewReq,
        auth: Authentication,
    ): ReviewRes {
        validateMemberAuth(req.email, auth)

        return reviewService.saveReview(req)
    }

    @GetMapping("/list/{page}")
    fun getReviewList(
        @PathVariable page: Int,
    ): List<ReviewDetailRes> {
        return reviewService.findReviewList(page)
    }

    @GetMapping("{reviewId}")
    fun findReviewByReviewId(
        @PathVariable reviewId: Long,
    ): ReviewDetailRes? {
        return reviewService.findReviewByReviewId(reviewId)
    }

    @PutMapping
    fun updateReview(
        @ModelAttribute req: UpdateReviewReq,
        auth: Authentication,
    ): ReviewRes {
        validateMemberAuth(req.email, auth)

        return reviewService.updateReview(req)
    }

    @DeleteMapping
    fun deleteReview(
        @ModelAttribute req: DeleteReveiwReq,
        auth: Authentication,
    ): Boolean {
        validateMemberAuth(req.email, auth)

        return reviewService.deleteReview(req)
    }

    private fun validateMemberAuth(
        email: String,
        auth: Authentication,
    ) {
        if (email != auth.name) {
            throw BaseException(BaseResponseStatus.INVALID_MEMBER)
        }
    }
}
