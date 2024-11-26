package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.exception.BaseException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberMapper: MemberMapper,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun findMemberByMemberId(memberId: Long) = memberRepository.findMemberByMemberId(memberId)

    fun createMember(req: CreateMemberReq): Boolean {
        // 유효한 입력인지 검증
        validateCreateMember(req)

        val encryptedPassword = passwordEncoder.encode(req.password)

        memberRepository.save(
            memberMapper.toEntity(req, encryptedPassword),
        )

        return true
    }

    private fun validateCreateMember(req: CreateMemberReq) {
        // 이메일 중복 검사
        if (req.email != null &&
            memberRepository.existsByEmail(req.email)
        ) {
            throw BaseException(BaseResponseStatus.ALREADY_EXISTS, "이메일")
        }

        // 닉네임 중복 검사
        if (req.nickname != null &&
            memberRepository.existsByNickname(req.nickname)
        ) {
            throw BaseException(BaseResponseStatus.ALREADY_EXISTS, "닉네임")
        }

        // 비밀번호, 비밀번호 확인 일치 검사
        if (req.password != req.passwordConfirm) {
            throw BaseException(BaseResponseStatus.PASSWORD_MISMATCH)
        }
    }
}
