package com.example.oauthsecurity.model

import com.fasterxml.jackson.annotation.JsonProperty


data class OAuthToken(
        @JsonProperty("access_token")
        val accessToken:String,
        @JsonProperty("token_type")
        val tokenType:String,
        @JsonProperty("refresh_token")
        val refreshToken:String?,
        @JsonProperty("expires_in")
        val expiresIn:Long,
        @JsonProperty("scope")
        val scope:String,
        @JsonProperty("jti")
        val jti:String?
){

}