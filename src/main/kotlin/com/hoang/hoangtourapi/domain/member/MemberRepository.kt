package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>
