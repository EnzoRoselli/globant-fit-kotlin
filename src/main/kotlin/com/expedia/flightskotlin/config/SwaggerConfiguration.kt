package com.expedia.flightskotlin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.sql.Timestamp

@Configuration
@EnableSwagger2
class SwaggerConfiguration {
    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false).select()
            .apis(RequestHandlerSelectors.basePackage("com.expedia.flightskotlin"))
            .paths(PathSelectors.ant("/flights/**"))
            .build()
            .directModelSubstitute(Timestamp::class.java, String::class.java)
}