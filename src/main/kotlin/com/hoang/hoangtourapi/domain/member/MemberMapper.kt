package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.domain.member.model.Member
import org.mapstruct.Mapper

@Mapper(
    componentModel = "spring",
)
interface MemberMapper {
    fun toEntity(req: CreateMemberReq): Member
}
