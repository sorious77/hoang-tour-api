package com.hoang.hoangtourapi.repository

import com.hoang.hoangtourapi.model.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

}