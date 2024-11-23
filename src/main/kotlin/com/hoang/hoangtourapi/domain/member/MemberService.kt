package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.domain.member.model.Member
import com.hoang.hoangtourapi.exception.AlreadyExistsException
import com.hoang.hoangtourapi.exception.PasswordMismatchException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberMapper: MemberMapper,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun findMemberByMemberId(memberId: Long) = memberRepository.findMemberByMemberId(memberId)

    fun createMember(req: CreateMemberReq): Member? {
        // 유효한 입력인지 검증
        validateCreateMember(req)

        val encryptedPassword = passwordEncoder.encode(req.password)

        val member =
            memberRepository.save(
                memberMapper.toEntity(req, encryptedPassword),
            )

        return member
    }

    private fun validateCreateMember(req: CreateMemberReq) {
        // 이메일 중복 검사
        if (req.email != null &&
            memberRepository.existsByEmail(req.email)
        ) {
            throw AlreadyExistsException("이메일")
        }

        // 닉네임 중복 검사
        if (req.nickname != null &&
            memberRepository.existsByNickname(req.nickname)
        ) {
            throw AlreadyExistsException("닉네임")
        }

        // 비밀번호, 비밀번호 확인 일치 검사
        if (req.password != req.passwordConfirm) throw PasswordMismatchException()
    }
}
