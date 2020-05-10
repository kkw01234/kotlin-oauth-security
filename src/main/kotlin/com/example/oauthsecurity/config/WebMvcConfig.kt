package com.example.oauthsecurity.config

import com.example.oauthsecurity.filter.VIsionFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebMvcConfig: WebMvcConfigurer {
    private val MAX_AGE_SECONDS: Long = 3600

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECONDS)
    }

    @Bean
    fun getRestTemplate(): RestTemplate? {
        return RestTemplate()
    }

    @Bean
    fun getFilterRegistrationBean(): FilterRegistrationBean<VIsionFilter> {
        val filterRegistrationBean: FilterRegistrationBean<VIsionFilter> = FilterRegistrationBean(VIsionFilter())
        filterRegistrationBean.setName("LoggingFilter")
        filterRegistrationBean.addUrlPatterns("/oauth2/token", "/health_check.html")
        return filterRegistrationBean
    }
}