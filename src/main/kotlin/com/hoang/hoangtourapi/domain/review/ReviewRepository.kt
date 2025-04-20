package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.image.model.Image
import com.hoang.hoangtourapi.domain.member.model.Member
import com.hoang.hoangtourapi.domain.member.model.ProfileReq
import com.hoang.hoangtourapi.domain.member.model.ProfileRes
import com.hoang.hoangtourapi.domain.review.model.Review
import com.hoang.hoangtourapi.domain.review.model.ReviewDetailRes
import com.hoang.hoangtourapi.domain.review.model.ReviewImage
import com.hoang.hoangtourapi.domain.review.model.ReviewRes
import com.hoang.hoangtourapi.domain.station.model.Station
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.enums.Status
import com.hoang.hoangtourapi.exception.BaseException
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface ReviewRepository : JpaRepository<Review, Long>, CustomReviewRepository {
    fun findReviewByReviewId(reviewId: Long): Review?
}

interface CustomReviewRepository {
    fun findReviewByNickname(req: ProfileReq): List<ProfileRes.ProfileReviewRes>?

    fun findReviewListPaging(page: Int): List<ReviewDetailRes>

    fun findReviewDetailByReviewId(reviewId: Long): ReviewDetailRes?
}

@Component
class CustomReviewRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
    private val reviewMapper: ReviewMapper,
) : CustomReviewRepository {
    override fun findReviewByNickname(req: ProfileReq): List<ProfileRes.ProfileReviewRes>? {
        val pageable = PageRequest.of(req.pageNumber?.let { req.pageNumber - 1 } ?: 0, 20)

        return executor
            .findPage(pageable) {
                selectNew<ProfileRes.ProfileReviewRes>(path(Review::reviewId), path(Image::imageUrl))
                    .from(
                        entity(Review::class),
                        join(Member::class).on(path(Review::memberId).equal(path(Member::memberId))),
                        join(Image::class).on(
                            path(Review::reviewId).equal(path(Image::reviewId))
                                .and(path(Image::imageOrder).equal(1)),
                        ),
                    )
                    .where(
                        path(Member::nickname).equal(req.nickname)
                            .and(path(Review::status).eq(Status.ACTIVE)),
                    )
                    .orderBy(path(Review::insDate).desc())
            }
            .filterNotNull()
    }

    override fun findReviewListPaging(page: Int): List<ReviewDetailRes> {
        val pageable = PageRequest.of(page, 20)

        val reviewList =
            executor
                .findPage(pageable) {
                    selectNew<ReviewRes>(
                        path(Review::reviewId),
                        path(Review::title),
                        path(Review::contents),
                        path(Member::nickname),
                        path(Member::email),
                        path(Review::insDate),
                        path(Review::insDate).equal(path(Review::updDate)).`as`(expression("isModified")),
                        path(Station::stationName),
                    )
                        .from(
                            entity(Review::class),
                            join(Member::class).on(path(Review::memberId).equal(path(Member::memberId))),
                            join(Station::class).on(path(Review::stationId).equal(path(Station::stationId))),
                        )
                        .where(
                            path(Review::status).eq(Status.ACTIVE),
                        )
                        .orderBy(path(Review::insDate).desc())
                }
                .filterNotNull()

        val reviewIdList = reviewList.map { it.reviewId }
        val reviewImages =
            executor.findAll {
                selectNew<ReviewImage>(
                    path(Review::reviewId),
                    path(Image::imageUrl),
                    path(Image::imageOrder),
                ).from(
                    entity(Review::class),
                    join(Image::class).on(path(Review::reviewId).eq(path(Image::reviewId))),
                ).where(
                    path(Image::status).eq(Status.ACTIVE)
                        .and(path(Review::reviewId).`in`(reviewIdList)),
                )
            }.mapNotNull { it }

        val reviewImageMap =
            reviewImages.groupBy { it.reviewId }
                .mapValues { entry ->
                    entry.value.sortedBy { it.order }
                }

        return reviewList.map {
            val reviewImageList = reviewImageMap[it.reviewId] ?: throw Exception()

            reviewMapper.toReviewDetailRes(it, reviewImageList)
        }
    }

    override fun findReviewDetailByReviewId(reviewId: Long): ReviewDetailRes? {
        val reviewList =
            executor.findAll {
                selectNew<ReviewRes>(
                    path(Review::reviewId),
                    path(Review::title),
                    path(Review::contents),
                    path(Member::nickname),
                    path(Member::email),
                    path(Review::insDate),
                    path(Review::insDate).equal(path(Review::updDate)).`as`(expression("isModified")),
                    path(Station::stationName),
                )
                    .from(
                        entity(Review::class),
                        join(Member::class).on(path(Review::memberId).equal(path(Member::memberId))),
                        join(Station::class).on(path(Review::stationId).equal(path(Station::stationId))),
                    )
                    .where(
                        path(Review::status).eq(Status.ACTIVE)
                            .and(path(Review::reviewId).eq(reviewId)),
                    )
            }.filterNotNull()

        if (reviewList.isEmpty()) {
            throw BaseException(BaseResponseStatus.EMPTY_RESULT)
        }

        val review = reviewList[0]

        val reviewImages =
            executor.findAll {
                selectNew<ReviewImage>(
                    path(Review::reviewId),
                    path(Image::imageUrl),
                    path(Image::imageOrder),
                ).from(
                    entity(Review::class),
                    join(Image::class).on(path(Review::reviewId).eq(path(Image::reviewId))),
                ).where(
                    path(Image::status).eq(Status.ACTIVE)
                        .and(path(Review::reviewId).eq(reviewId)),
                )
            }.mapNotNull { it }

        return reviewMapper.toReviewDetailRes(review, reviewImages)
    }
}
