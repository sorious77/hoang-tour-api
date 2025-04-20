package com.hoang.hoangtourapi.domain.image

import com.hoang.hoangtourapi.domain.review.model.SaveReviewReq
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.util.UUID

@Service
class ImageService(
    private val imageMapper: ImageMapper,
    private val imageRepository: ImageRepository,
) {
    @Value("\${image.basePath}")
    lateinit var imageBasePath: String

    /**
     * 이미지를 로컬에 저장하고, 그 정보를 DB에도 저장
     */
    @Transactional
    fun saveImageList(
        reviewId: Long,
        imageList: List<MultipartFile>,
        req: SaveReviewReq,
    ) {
        val pathList = saveMultiImageFile(imageList)

        pathList.forEachIndexed { order, path ->
            imageRepository.save(
                imageMapper.toEntity(reviewId, path, order + 1, req),
            )
        }
    }

    /**
     * 이미지를 로컬에 저장
     */
    fun saveMultiImageFile(imageList: List<MultipartFile>): List<String> {
        return imageList.map { image ->
            val filename = image.originalFilename

            val extension = filename?.substring(filename.lastIndexOf(".") + 1) ?: ""
            val uuid = UUID.randomUUID()
            val path = "${Paths.get(System.getProperty("user.home"), imageBasePath)}/$uuid.$extension"

            image.transferTo(File(path))
            "$uuid.$extension"
        }
    }
}
