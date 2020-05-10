package com.example.oauthsecurity

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class OauthSecurityApplicationTests {

    @Autowired
    private lateinit var passwordEncoder:PasswordEncoder

    @Test
    fun contextLoads() {
        println("testSecret : ${passwordEncoder.encode("testSecret")}")
    }

}
