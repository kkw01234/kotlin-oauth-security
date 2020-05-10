package com.example.oauthsecurity.model

import com.example.oauthsecurity.repository.UserRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class CustomAuthenticationProvider(
        val userRepository:UserRepository
):AuthenticationProvider{
    override fun authenticate(authentication: Authentication): Authentication {
        val name = authentication.name
        val password = authentication.credentials.toString()
        val user: User? = userRepository.findByUid(name)
//        if (!passwordEncoder.matches(password, user?.password)) throw BadCredentialsException("password is not valid")
        return UsernamePasswordAuthenticationToken(name, password, user?.authorities)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication!! == UsernamePasswordAuthenticationToken::class.java
    }

}