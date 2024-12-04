package com.hoang.hoangtourapi.domain.follow.model

import com.hoang.hoangtourapi.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "FOLLOW_INFOS")
class FollowInfo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLLOW_ID", nullable = false)
    var followId: Long,
    @Column(name = "FOLLOWER_ID", nullable = false)
    var followerId: Long,
    @Column(name = "FOLLOWING_ID", nullable = false)
    var followingId: Long,
) : BaseEntity()
