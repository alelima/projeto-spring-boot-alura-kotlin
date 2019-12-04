package br.com.alura.forum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.web.config.EnableSpringDataWebSupport
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
class ForumApplication

fun main(args: Array<String>) {
	runApplication<ForumApplication>(*args)
}
