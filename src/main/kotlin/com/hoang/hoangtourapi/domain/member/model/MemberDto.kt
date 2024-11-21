package com.hoang.hoangtourapi.domain.member.model

data class CreateMemberReq(
    val email: String,
    val nickname: String,
    val password: String,
)

data class MemberDto(
    val name: String,
)
