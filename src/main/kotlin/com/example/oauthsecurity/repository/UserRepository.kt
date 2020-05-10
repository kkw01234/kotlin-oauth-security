package com.example.oauthsecurity.repository

import com.example.oauthsecurity.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<User,Long>{
    fun findByUid(email:String): User?

}
