package com.hoang.hoangtourapi.domain.image

import com.hoang.hoangtourapi.domain.image.model.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long>
