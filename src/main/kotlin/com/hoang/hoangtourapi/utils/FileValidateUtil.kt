package com.hoang.hoangtourapi.utils

import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.exception.BaseException

object FileValidateUtil {
    private val IMAGE_EXTENSIONS = listOf("jpg", "png", "gif", "webp")

    fun checkImageFormat(fileName: String) {
        val extensionIndex = fileName.indexOf('.')

        if (extensionIndex == -1) {
            throw BaseException(BaseResponseStatus.FILE_EXTENSION_NOT_EXISTS)
        }

        val extension = fileName.substring(extensionIndex + 1).lowercase()

        require(IMAGE_EXTENSIONS.contains(extension)) {
            throw BaseException(BaseResponseStatus.FILE_EXTENSION_NOT_SUPPORTED)
        }
    }
}
