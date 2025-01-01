package com.hoang.hoangtourapi.domain.member.auth

import com.hoang.hoangtourapi.domain.member.MemberRepository
import com.hoang.hoangtourapi.enums.MemberType
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(
    private val memberRepository: MemberRepository,
) : UserDetailsService {
    override fun loadUserByUsername(email: String): MemberDetails {
        val member =
            memberRepository.findMemberByEmail(email)
                ?: throw EntityNotFoundException()

        val authorities: MutableList<SimpleGrantedAuthority> = mutableListOf()

        authorities.add(
            SimpleGrantedAuthority(
                when (member.memberType) {
                    MemberType.ADMIN -> "ADMIN"
                    else -> "BASIC"
                },
            ),
        )

        return MemberDetails(email, member.password, member.nickname, member.introduction ?: "", authorities)
    }
}
