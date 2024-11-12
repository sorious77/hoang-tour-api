package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.domain.member.model.Member
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberMapper: MemberMapper,
    private val memberRepository: MemberRepository,
) {
    val members =
        mutableListOf(
            Member(1, "member 1", "member1@gmail.com"),
            Member(2, "member 2", "member2@gmail.com"),
            Member(3, "member 3", "member3@gmail.com"),
        )

    fun findAll(): List<Member>? {
//        return memberRepository.findAll()
        return members
    }

    fun createMember(req: CreateMemberReq): Member? {
        val member = Member(members.size.toLong(), req.name, req.email)
        members.add(member)

        return member
    }
}
