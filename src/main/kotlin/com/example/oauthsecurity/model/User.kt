package com.example.oauthsecurity.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class User(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        private val msrl: Long? = null,
        @Column(nullable = false, unique = true, length = 50)
        private val uid: String? = null,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(length = 100)
        private val password: String? = null,
        @Column(nullable = false, length = 100)
        private val name: String? = null,
        @Column(length = 100)
        private val provider: String? = null,
        @ElementCollection(fetch = EAGER)
        private val roles: List<String> = ArrayList()
): UserDetails{
    fun getUid():String?{
        return this.uid
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.roles.stream().map{ SimpleGrantedAuthority(it) }.collect(Collectors.toList())
    }

    override fun isEnabled(): Boolean {
       return true
    }

    override fun getUsername(): String? {
       return this.uid
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String? {
        return password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

}