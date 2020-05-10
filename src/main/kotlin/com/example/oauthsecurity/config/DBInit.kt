package com.example.oauthsecurity.config

import com.example.oauthsecurity.model.OAuthClientDetails
import com.example.oauthsecurity.model.User
import com.example.oauthsecurity.repository.OAuthClientDetailsRepository
import com.example.oauthsecurity.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class DBInit(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder, val oAuthClientDetailsRepository: OAuthClientDetailsRepository):CommandLineRunner{

    override fun run(vararg args: String?) {
        userRepository.save(User(uid = "test", password = passwordEncoder.encode("1234"),name = "test",roles = Collections.singletonList("ROLE_USER")))
        oAuthClientDetailsRepository.save(OAuthClientDetails("testClientId",null,"${passwordEncoder.encode("testSecret")}","read,write",
        "authorization_code,refresh_token","http://localhost:8081/oauth2/callback","ROLE_USER",36000,50000,null,null))
    }

}