package com.example.API_reservas_clases.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    private lateinit var rsaKeys: RSAKeysProperties

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.POST,"/usuarios/login").permitAll()
                    .requestMatchers(HttpMethod.POST,"/usuarios/register").permitAll()
                    .requestMatchers(HttpMethod.GET,"/usuarios/{id}").authenticated()
                    .requestMatchers(HttpMethod.GET, "/usuarios/getAll").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/usuarios/update/{id}").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/usuarios/delete/{id}").authenticated()
                    .requestMatchers(HttpMethod.GET, "/reservas/{id}").authenticated()
                    .requestMatchers(HttpMethod.GET, "/reservas/getAll").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/reservas/register").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/reservas/update/{id}").authenticated()
                    .requestMatchers(HttpMethod.DELETE,"/reservas/delete/{id}").authenticated()
                    .requestMatchers(HttpMethod.POST, "/clases/register").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/clases/update/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE,"/clases/delete/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/clases/{id}").authenticated()
                    .requestMatchers(HttpMethod.GET, "/clases/getAll").authenticated()
                    .anyRequest().permitAll()
            } // Los recursos protegidos y públicos
            .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    /*
    MÉTODO PARA CODIFICAR UN JWT
     */
    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaKeys.publicKey).privateKey(rsaKeys.privateKey).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))

        return NimbusJwtEncoder(jwks)
    }



    /*
    MÉTODO PARA CODIFICAR UN JWT
    */
    @Bean
    fun jwtDecoder(): JwtDecoder {

        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey).build()
    }
}