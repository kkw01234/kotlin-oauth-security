package com.example.oauthsecurity.repository

import com.example.oauthsecurity.model.OAuthClientDetails
import org.springframework.data.jpa.repository.JpaRepository

interface OAuthClientDetailsRepository:JpaRepository<OAuthClientDetails, String>{
}