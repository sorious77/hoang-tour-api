package com.hoang.hoangtourapi.enums

enum class BaseResponseStatus(val code: Int, val description: String) {
    SUCCESS(1000, "요청에 성공했습니다."),

    /**
     * Business 로직
     */
    EMPTY_RESULT(2000, "값이 존재하지 않습니다."),
    ALREADY_EXISTS(2001, "이미 존재하는 값입니다."),
    PASSWORD_MISMATCH(2002, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    ENTITY_NOT_FOUND(2003, "존재하지 않는 데이터입니다."),
    SIGN_IN_FAIL(2004, "로그인에 실패했습니다."),
    EXPIRED_SESSION(2005, "세션이 만료되었습니다."),
    INVALID_TOKEN(2006, "유효하지 않은 토큰입니다."),

    /**
     * 서버 에러
     */
    SERVER_ERROR(4000, "처리 중 에러가 발생했습니다."),
    INVALID_INPUT(4001, "올바르지 않은 입력 값입니다."),
    ;

    companion object {
        fun fromCode(code: Int): BaseResponseStatus {
            return entries.firstOrNull { it.code == code } ?: SERVER_ERROR
        }
    }
}
