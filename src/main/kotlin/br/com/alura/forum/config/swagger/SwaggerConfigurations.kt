package br.com.alura.forum.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.schema.ModelRef
import springfox.documentation.builders.ParameterBuilder
import br.com.alura.forum.model.Usuario
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Parameter
import springfox.documentation.spi.DocumentationType



@Configuration
class SwaggerConfigurations {

    @Bean
    fun forumApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(Usuario::class.java)
                .globalOperationParameters(
                        listOf<Parameter>(
                                ParameterBuilder()
                                        .name("Authorization")
                                        .description("Header para Token JWT")
                                        .modelRef(ModelRef("string"))
                                        .parameterType("header")
                                        .required(false)
                                        .build()))
    }
}