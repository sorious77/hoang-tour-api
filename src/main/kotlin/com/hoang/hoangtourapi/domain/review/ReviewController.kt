package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.review.model.ReviewDetailRes
import com.hoang.hoangtourapi.domain.review.model.ReviewRes
import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.exception.BaseException
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reviews")
class ReviewController(
    private val reviewService: ReviewService,
) {
    @PostMapping
    fun saveReview(
        @RequestBody req: SaveReviewReq,
        auth: Authentication,
    ): ReviewRes {
        if (req.email != auth.name) {
            throw BaseException(BaseResponseStatus.INVALID_MEMBER)
        }

        return reviewService.saveReview(req)
    }

    @GetMapping("/list/{page}")
    fun getReviewList(
        @PathVariable page: Int,
    ): List<ReviewDetailRes> {
        return reviewService.getReviewList(page)
    }
}
