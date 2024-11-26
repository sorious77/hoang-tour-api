package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.domain.member.model.Member
import com.hoang.hoangtourapi.domain.member.model.MemberDetailsService
import com.hoang.hoangtourapi.domain.member.model.SignInReq
import com.hoang.hoangtourapi.domain.member.model.SignInRes
import com.hoang.hoangtourapi.utils.JwtTokenUtil
import jakarta.validation.Valid
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
class MemberController(
    private val memberService: MemberService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val memberDetailsService: MemberDetailsService,
) {
    @GetMapping
    fun getMemberByMemberId(
        @RequestParam memberId: Long,
    ): Member? {
        return memberService.findMemberByMemberId(memberId)
    }

    @PostMapping
    fun createMember(
        @Valid @RequestBody req: CreateMemberReq,
    ): Boolean {
        return memberService.createMember(req)
    }

    @PostMapping("/signIn")
    fun signIn(
        @Valid @RequestBody req: SignInReq,
    ): SignInRes? {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(req.email, req.password),
        )

        val memberDetails = memberDetailsService.loadUserByUsername(req.email ?: "")
        val token = jwtTokenUtil.generateToken(memberDetails)

        return SignInRes(memberDetails.getNickname(), token)
    }
}
