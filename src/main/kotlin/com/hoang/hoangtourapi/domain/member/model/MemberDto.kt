package com.hoang.hoangtourapi.domain.member.model

data class CreateMemberReq(
    val email: String,
    val name: String,
)

data class MemberDto(
    val name: String,
)
