package com.hoang.hoangtourapi.domain.member.model

import com.hoang.hoangtourapi.validator.EmailValid
import com.hoang.hoangtourapi.validator.PasswordValid
import jakarta.validation.constraints.NotEmpty

data class CreateMemberReq(
    @field:EmailValid
    val email: String?,
    @field:NotEmpty(message = "닉네임은 필수값입니다.")
    val nickname: String?,
    @field:PasswordValid
    val password: String?,
    @field:NotEmpty(message = "비밀번호 확인은 필수값입니다.")
    val passwordConfirm: String?,
)

data class MemberDto(
    val name: String,
)
