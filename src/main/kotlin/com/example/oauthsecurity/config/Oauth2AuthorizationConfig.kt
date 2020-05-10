package com.example.oauthsecurity.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class Oauth2AuthorizationConfig(
        val passwordEncoder: PasswordEncoder,
        @Value("\${security.oauth2.jwt.signKey}")val signKey:String,
        val dataSource: DataSource
): AuthorizationServerConfigurerAdapter(){
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("testClientId")
                .secret(passwordEncoder.encode("testSecret"))
                .redirectUris("http://localhost:8081/oauth2/callback")
                .authorizedGrantTypes("authorization_code")
                .authorizedGrantTypes("refresh_token")
                .scopes("read","write")
                .accessTokenValiditySeconds(30000)
//        clients.jdbc(dataSource).passwordEncoder(passwordEncoder)
    }
/** jwt Token */
//    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
//        super.configure(endpoints)
//        endpoints.accessTokenConverter(jwtAccessTokenConverter())
//    }
/** jdbc */
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
//        super.configure(endpoints)
        endpoints.tokenStore(JdbcTokenStore(dataSource))
    }

    @Bean
    fun jwtAccessTokenConverter():JwtAccessTokenConverter{
        val converter:JwtAccessTokenConverter = JwtAccessTokenConverter()
        converter.setSigningKey(signKey)
        return converter
    }
}