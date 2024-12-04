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
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", nullable = false)
    var memberId: Long,
    @Column(name = "EMAIL", unique = true, nullable = false)
    var email: String,
    @Column(name = "NICKNAME", unique = true, nullable = false)
    var nickname: String,
    @Column(name = "PASSWORD", nullable = false)
    var password: String,
    @Column(name = "INTRODUCTION", nullable = true)
    var introduction: String? = "",
    @Enumerated(value = EnumType.STRING)
    @Column(name = "MEMBER_TYPE", nullable = false)
    var memberType: MemberType = MemberType.BASIC,
    @Column(name = "PROFILE_IMAGE", nullable = true)
    var profileImage: String?,
) : BaseEntity()
