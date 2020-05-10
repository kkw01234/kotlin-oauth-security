package com.example.oauthsecurity.controller

import com.example.oauthsecurity.model.OAuthToken
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.codec.binary.Base64
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate


@RestController
@RequestMapping("/oauth2")
class Oauth2Controller(
        private val restTemplate:RestTemplate
){
    companion object {
        private val objectMapper = jacksonObjectMapper()
    }

    @ExperimentalStdlibApi
    @GetMapping("/callback")
    fun callbackSocial(@RequestParam code:String):OAuthToken?{
        val credentials:String = "testClientId:testSecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.encodeToByteArray()))
        val headers:HttpHeaders = HttpHeaders()
        headers.add("Authorization", "Basic $encodedCredentials")
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("code", code)
        params.add("grant_type", "authorization_code")
        params.add("redirect_uri","http://localhost:8081/oauth2/callback")
        val request:HttpEntity<MultiValueMap<String, String>> = HttpEntity(params, headers)
        println("$params $headers")
        val response: ResponseEntity<String> = restTemplate.postForEntity("http://localhost:8081/oauth/token",request, String::class.java)
        if(response.statusCode == HttpStatus.OK){
            return objectMapper.readValue(response.body, OAuthToken::class.java)
        }
        return null
    }

    @GetMapping("/token/refresh")
    fun refreshToken(@RequestParam refreshToken:String):OAuthToken?{
        val credentials = "testClientId:testSecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.add("Authorization", "Basic $encodedCredentials")
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("refresh_token", refreshToken)
        params.add("grant_type", "refresh_token")
        val request = HttpEntity(params, headers)
        val response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request, String::class.java)
        return if (response.statusCode == OK) {
           objectMapper.readValue(response.body, OAuthToken::class.java)
        } else null
    }
}