package com.hoang.hoangtourapi.domain.image

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.hoang.hoangtourapi.enums.BaseResponseStatus
import com.hoang.hoangtourapi.exception.BaseException
import com.hoang.hoangtourapi.utils.FileValidateUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ImageS3Service(
    @Value("\${cloud.aws.s3.bucketName}")
    private val bucket: String,
    private val amazonS3: AmazonS3,
) {
    companion object {
        const val TYPE_IMAGE = "image"
    }

    fun uploadImage(multipartFile: MultipartFile): String {
        val originalFilename =
            multipartFile.originalFilename
                ?: throw BaseException(BaseResponseStatus.FILE_NAME_WRONG_FORMAT)

        FileValidateUtil.checkImageFormat(originalFilename)

        val fileName = "${UUID.randomUUID()}-$originalFilename"
        val objectMetadata =
            setFileDateOption(
                TYPE_IMAGE,
                getFileExtension(originalFilename),
                multipartFile,
            )

        val result = amazonS3.putObject(bucket, fileName, multipartFile.inputStream, objectMetadata)

        return fileName
    }

    fun getFile(fileName: String): String {
        return amazonS3.getUrl(bucket, fileName).toString()
    }

    fun uploadMultiImage(fileList: List<MultipartFile>) {
        fileList.map { file -> uploadImage(file) }
    }

    private fun getFileExtension(fileName: String): String {
        val extensionIndex = fileName.lastIndexOf('.')
        return fileName.substring(extensionIndex + 1)
    }

    private fun setFileDateOption(
        type: String,
        file: String,
        multipartFile: MultipartFile,
    ): ObjectMetadata {
        val objectMetadata = ObjectMetadata()
        objectMetadata.apply {
            contentType = "/$type/${getFileExtension(file)}"
            contentLength = multipartFile.inputStream.available().toLong()
        }
        return objectMetadata
    }
}
