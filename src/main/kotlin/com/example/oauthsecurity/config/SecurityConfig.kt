package com.example.oauthsecurity.config

import com.example.oauthsecurity.model.CustomAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
        val authenticationProvider:CustomAuthenticationProvider
): WebSecurityConfigurerAdapter(){

    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("a"))
//                .roles("USER")
        auth.authenticationProvider(authenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(STATELESS)
//                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/","/oauth/token","/oauth/**","oauth2/callback","h2-consloe/*")
                .permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic()
//                .antMatchers("/v1/users").access("#oauth2.hasScope('read')")
    }
//    @Bean
//    fun authentication(): Authentication{
//        val request: Authentication = UsernamePasswordAuthenticationToken(name, password)
//    }

    @Bean
    fun passwordEncoder():PasswordEncoder{
        return BCryptPasswordEncoder()
    }
}