package com.example.oauthsecurity.controller

import com.example.oauthsecurity.model.User
import com.example.oauthsecurity.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HealthController(
        private val userRepository: UserRepository
){
    @GetMapping("health")
    fun health():String{
        return "health"
    }

    @GetMapping("/v1/users")
    fun findAllUser():List<User>{
        return userRepository.findAll()
    }

}