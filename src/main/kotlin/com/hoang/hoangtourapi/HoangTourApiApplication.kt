package com.hoang.hoangtourapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class HoangTourApiApplication

fun main(args: Array<String>) {
    runApplication<HoangTourApiApplication>(*args)
}
