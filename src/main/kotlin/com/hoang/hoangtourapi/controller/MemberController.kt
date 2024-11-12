package com.hoang.hoangtourapi.controller

import com.hoang.hoangtourapi.model.dto.CreateMemberReq
import com.hoang.hoangtourapi.model.entity.Member
import com.hoang.hoangtourapi.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping
    fun getMember(): List<Member>? {
        return memberService.findAll()
    }

    @PostMapping
    fun createMember(req: CreateMemberReq): Member? {
        return memberService.createMember(req)
    }
}