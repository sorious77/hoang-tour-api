package com.hoang.hoangtourapi.domain.image

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/image/test")
class ImageController(
    private val imageService: ImageService,
) {
    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
    )
    fun saveMultiImage(fileList: List<MultipartFile>): Any? {
        return imageService.saveMultiImageFile(fileList)
    }
}
