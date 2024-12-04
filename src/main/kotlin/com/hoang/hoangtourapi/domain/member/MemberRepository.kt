package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findMemberByMemberId(memberId: Long): Member?

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean

    fun findMemberByEmail(email: String): Member?

    fun findMemberByNickname(nickname: String): Member?
}
