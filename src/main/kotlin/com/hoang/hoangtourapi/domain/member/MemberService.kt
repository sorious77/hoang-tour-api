package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.follow.FollowRepository
import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.domain.member.model.ProfileReq
import com.hoang.hoangtourapi.domain.member.model.ProfileRes
import com.hoang.hoangtourapi.domain.review.ReviewRepository
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.exception.BaseException
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberMapper: MemberMapper,
    private val memberRepository: MemberRepository,
    private val reviewRepository: ReviewRepository,
    private val followRepository: FollowRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun findMemberByMemberId(memberId: Long) = memberRepository.findMemberByMemberId(memberId)

    @Transactional
    fun createMember(req: CreateMemberReq): Boolean {
        // 유효한 입력인지 검증
        validateCreateMember(req)

        val encryptedPassword = passwordEncoder.encode(req.password)

        memberRepository.save(
            memberMapper.toEntity(req, encryptedPassword),
        )

        return true
    }

    fun findMemberProfileByNickname(req: ProfileReq): ProfileRes? {
        val member =
            memberRepository.findMemberByNickname(req.nickname!!)
                ?: throw EntityNotFoundException()

        val reviews = reviewRepository.findReviewByNickname(req)

        val followCount = followRepository.findFollowCountByNickname(req.nickname)

        return ProfileRes(
            nickname = member.nickname,
            introduction = member.introduction ?: "",
            reviews = reviews ?: listOf(),
            followCount.first,
            followCount.second,
        )
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
