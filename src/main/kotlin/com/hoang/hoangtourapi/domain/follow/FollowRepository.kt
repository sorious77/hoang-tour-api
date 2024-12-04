package com.hoang.hoangtourapi.domain.follow

import com.hoang.hoangtourapi.domain.follow.model.FollowInfo
import com.hoang.hoangtourapi.domain.member.model.Member
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface FollowRepository : JpaRepository<FollowInfo, Long>, CustomFollowRepository

interface CustomFollowRepository {
    fun findFollowCountByNickname(nickname: String): Pair<Long, Long>
}

@Component
class CustomFollowRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
) : CustomFollowRepository {
    override fun findFollowCountByNickname(nickname: String): Pair<Long, Long> {
        val followCount =
            executor
                .findAll {
                    select(count(path(FollowInfo::followId)))
                        .from(
                            entity(FollowInfo::class),
                            join(Member::class).on(path(FollowInfo::followingId).equal(path(Member::memberId))),
                        )
                        .where(
                            path(Member::nickname).equal(nickname),
                        )
                }[0] ?: 0L

        val followingCount =
            executor
                .findAll {
                    select(count(path(FollowInfo::followId)))
                        .from(
                            entity(FollowInfo::class),
                            join(Member::class).on(path(FollowInfo::followerId).equal(path(Member::memberId))),
                        )
                        .where(
                            path(Member::nickname).equal(nickname),
                        )
                }[0] ?: 0L

        return Pair(followCount, followingCount)
    }
}
