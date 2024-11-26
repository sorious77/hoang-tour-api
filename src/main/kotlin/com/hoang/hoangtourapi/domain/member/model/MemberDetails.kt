package com.hoang.hoangtourapi.domain.member.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MemberDetails(
    private val username: String,
    private val password: String,
    private val nickname: String,
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {
    override fun getAuthorities() = authorities

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    fun getNickname() = nickname // 닉네임 반환
}
