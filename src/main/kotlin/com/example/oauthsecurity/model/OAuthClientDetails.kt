package com.example.oauthsecurity.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class OAuthClientDetails(
        @Id
        @Column(name = "client_id")
        private val clientId:String,
        @Column(name = "resource_ids")
        private val resourceIds: String?,
        @Column(name = "client_secret")
        private val clientSecret: String,
        @Column(name = "scope")
        private val scope:String,
        @Column(name = "authorized_grant_types")
        private val authorizedGrantTypes:String,
        @Column(name = "web_server_redirect_uri")
        private val webServerRedirectUri:String,
        @Column(name = "authorities")
        private val authorities:String,
        @Column(name = "access_token_validity")
        private val accessTokenValidity: Long,
        @Column(name = "refresh_token_validity")
        private val refreshTokenValidity: Long,
        @Column(name = "additional_information")
        private val additionalInformation: String?,
        @Column(name = "autoapprove")
        private val autoApproval: String?
)
