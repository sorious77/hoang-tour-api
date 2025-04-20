package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.auth.MemberDetailsService
import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.domain.member.model.Member
import com.hoang.hoangtourapi.domain.member.model.ProfileReq
import com.hoang.hoangtourapi.domain.member.model.ProfileRes
import com.hoang.hoangtourapi.domain.member.model.SignInReq
import com.hoang.hoangtourapi.domain.member.model.SignInRes
import com.hoang.hoangtourapi.utils.JwtTokenUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
@Tag(name = "Member", description = "회원 API")
class MemberController(
    private val memberService: MemberService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val memberDetailsService: MemberDetailsService,
    private val memberMapper: MemberMapper,
) {
    @GetMapping
    @Operation(
        summary = "ID로 회원 조회",
        description = "해당 ID를 가진 회원을 조회합니다.",
    )
    fun getMemberByMemberId(
        @RequestParam memberId: Long,
    ): Member? {
        return memberService.findMemberByMemberId(memberId)
    }

    @PostMapping
    @Operation(
        summary = "회원 생성",
        description = "새로운 회원을 생성합니다.",
    )
    fun createMember(
        @Valid @RequestBody req: CreateMemberReq,
    ): Boolean {
        return memberService.createMember(req)
    }

    @PostMapping("/signIn")
    @Operation(
        summary = "로그인",
        description = "ID와 비밀번호로 로그인을 합니다.",
    )
    fun signIn(
        @Valid @RequestBody req: SignInReq,
    ): SignInRes? {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(req.email, req.password),
        )

        val memberDetails = memberDetailsService.loadUserByUsername(req.email ?: "")
        val token = jwtTokenUtil.generateAccessToken(memberDetails)

        return memberMapper.toSignInRes(memberDetails, token)
    }

    @GetMapping("/profile")
    @Operation(
        summary = "닉네임에 해당하는 회원 조회",
        description = "해당 닉네임을 가진 회원 정보를 조회합니다.",
    )
    fun findMemberProfileByNickname(
        @ParameterObject @Valid req: ProfileReq,
    ): ProfileRes? {
        return memberService.findMemberProfileByNickname(req)
    }
}
