package com.hoang.hoangtourapi.domain.member.model

import com.hoang.hoangtourapi.common.BaseEntity
import com.hoang.hoangtourapi.enums.MemberType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "MEMBERS")
data class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", nullable = false)
    val memberId: Long,
    @Column(name = "EMAIL", nullable = false)
    val email: String,
    @Column(name = "NICKNAME", nullable = false)
    val nickname: String,
    @Column(name = "PASSWORD", nullable = false)
    val password: String,
    @Column(name = "INTRODUCTION", nullable = true)
    val introduction: String? = "",
    @Enumerated(value = EnumType.STRING)
    @Column(name = "MEMBER_TYPE", nullable = false)
    val memberType: MemberType = MemberType.BASIC,
) : BaseEntity()
