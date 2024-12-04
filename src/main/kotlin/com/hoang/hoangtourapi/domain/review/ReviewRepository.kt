package com.hoang.hoangtourapi.domain.review

import com.hoang.hoangtourapi.domain.image.model.Image
import com.hoang.hoangtourapi.domain.member.model.Member
import com.hoang.hoangtourapi.domain.member.model.ProfileReq
import com.hoang.hoangtourapi.domain.member.model.ProfileRes
import com.hoang.hoangtourapi.domain.review.model.Review
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface ReviewRepository : JpaRepository<Review, Long>, CustomReviewRepository

interface CustomReviewRepository {
    fun findReviewByNickname(req: ProfileReq): List<ProfileRes.ProfileReviewRes>?
}

@Component
class CustomReviewRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
) : CustomReviewRepository {
    override fun findReviewByNickname(req: ProfileReq): List<ProfileRes.ProfileReviewRes>? {
        val pageable = PageRequest.of(req.pageNumber ?: 0, 10)

        return executor
            .findPage(pageable) {
                selectNew<ProfileRes.ProfileReviewRes>(path(Review::reviewId), path(Image::imageUrl))
                    .from(
                        entity(Review::class),
                        join(Member::class).on(path(Review::memberId).equal(path(Member::memberId))),
                        join(Image::class).on(
                            path(Review::reviewId).equal(path(Image::reviewId))
                                .and(path(Image::order).equal(1)),
                        ),
                    )
                    .where(
                        path(Member::nickname).equal(req.nickname),
                    )
                    .orderBy(path(Review::insDate).desc())
            }
            .filterNotNull()
    }
}
