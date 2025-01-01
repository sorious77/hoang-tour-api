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

data class SignInReq(
    @field:NotEmpty(message = "이메일은 필수값입니다.")
    val email: String?,
    @field:NotEmpty(message = "비밀번호는 필수값입니다.")
    val password: String?,
)

data class SignInRes(
    val email: String,
    val nickname: String,
    val introduction: String?,
    val refreshToken: String,
    val accessToken: String,
)

data class ProfileReq(
    @field:NotEmpty(message = "닉네임은 필수값입니다.")
    val nickname: String?,
    val pageNumber: Int? = 0,
)

data class ProfileRes(
    val email: String,
    val nickname: String,
    val introduction: String,
    val reviews: List<ProfileReviewRes>,
    val totalFollowerCount: Long,
    val totalFollowingCount: Long,
) {
    data class ProfileReviewRes(
        val reviewId: Long,
        val imageUrl: String,
    )
}
