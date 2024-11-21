package com.hoang.hoangtourapi.domain.member

import com.hoang.hoangtourapi.domain.member.model.CreateMemberReq
import com.hoang.hoangtourapi.domain.member.model.Member
import com.hoang.hoangtourapi.enums.MemberType
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.NullValueMappingStrategy

@Mapper(
    componentModel = "spring",
    imports = [MemberType::class],
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
)
interface MemberMapper {
    @Mappings(
        Mapping(target = "memberType", expression = "java(MemberType.BASIC)"),
    )
    fun toEntity(req: CreateMemberReq): Member
}
