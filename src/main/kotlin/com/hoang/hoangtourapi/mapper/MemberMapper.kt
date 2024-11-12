package com.hoang.hoangtourapi.mapper

import com.hoang.hoangtourapi.model.dto.CreateMemberReq
import com.hoang.hoangtourapi.model.entity.Member
import org.mapstruct.Mapper

@Mapper(
    componentModel = "spring"
)
interface MemberMapper {
    fun toEntity(req: CreateMemberReq): Member
}