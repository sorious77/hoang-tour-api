package com.hoang.hoangtourapi.domain.line

import com.hoang.hoangtourapi.domain.line.model.LineRes
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/lines")
class LineController(
    private val lineService: LineService,
) {
    @GetMapping("/list")
    @Operation(
        summary = "호선 리스트 조회",
        description = "전체 호선 정보를 조회합니다.",
    )
    fun getLineList(): List<LineRes> {
        return lineService.getLineList()
    }
}
