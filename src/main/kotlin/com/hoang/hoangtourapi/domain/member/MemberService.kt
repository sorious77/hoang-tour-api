package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.domain.member.model.Member
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberMapper: MemberMapper,
    private val memberRepository: MemberRepository,
) {
    fun findMemberByMemberId(memberId: Long) = memberRepository.findMemberByMemberId(memberId)

    fun createMember(req: CreateMemberReq): Member? {
        println(memberMapper.toEntity(req).insOprt)

        val member =
            memberRepository.save(
                memberMapper.toEntity(req),
            )

        // TODO Password Encrypt

        return member
    }
}
