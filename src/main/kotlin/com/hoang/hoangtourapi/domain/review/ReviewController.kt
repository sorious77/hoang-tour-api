package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.review.model.DeleteReveiwReq
import com.hoang.hoangtourapi.domain.review.model.ReviewDetailRes
import com.hoang.hoangtourapi.domain.review.model.ReviewRes
import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import com.hoang.hoangtourapi.domain.review.model.UpdateReviewReq
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.exception.BaseException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "Review", description = "리뷰 API")
class ReviewController(
    private val reviewService: ReviewService,
) {
    @PostMapping
    @Operation(
        summary = "리뷰 저장",
        description = "새로운 리뷰를 저장합니다.",
    )
    fun saveReview(
        @ModelAttribute req: SaveReviewReq,
        auth: Authentication,
    ): ReviewRes {
        validateMemberAuth(req.email, auth)

        return reviewService.saveReview(req)
    }

    @GetMapping("/list/{page}")
    @Operation(
        summary = "리뷰 리스트 조회",
        description = "리뷰 리스트를 페이징해서 조회합니다.",
    )
    fun getReviewList(
        @PathVariable page: Int,
    ): List<ReviewDetailRes> {
        return reviewService.findReviewList(page)
    }

    @GetMapping("{reviewId}")
    @Operation(
        summary = "리뷰 단건 조회",
        description = "리뷰 단건을 조회합니다.",
    )
    fun findReviewByReviewId(
        @PathVariable reviewId: Long,
    ): ReviewDetailRes? {
        return reviewService.findReviewByReviewId(reviewId)
    }

    @PutMapping
    @Operation(
        summary = "리뷰 수정",
        description = "작성한 리뷰를 수정합니다.",
    )
    fun updateReview(
        @ModelAttribute req: UpdateReviewReq,
        auth: Authentication,
    ): ReviewRes {
        validateMemberAuth(req.email, auth)

        return reviewService.updateReview(req)
    }

    @DeleteMapping
    @Operation(
        summary = "리뷰 삭제",
        description = "작성한 리뷰를 삭제합니다.",
    )
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
