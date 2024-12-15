package com.example.API_reservas_clases.service

import com.example.API_reservas_clases.model.Usuario
import com.example.API_reservas_clases.repository.UsuarioRepository
import com.example.API_reservas_clases.security.SecurityConfig
import com.example.API_reservas_clases.util.validarUsuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService : UserDetailsService {


    @Autowired
    private lateinit var securityConfig: SecurityConfig

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository


    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario: Usuario = usuarioRepository
            .findByUsername(username)
            .orElseThrow()

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun registrarUsuario(usuario: Usuario): Usuario? {
        if(usuarioRepository.findByUsername(usuario.username).isPresent) {
            throw IllegalArgumentException("El usuario con el  nombre ${usuario.username} ya existe.")
        } else {
            usuario.password = securityConfig.passwordEncoder().encode(usuario.password)
            return usuarioRepository.save(usuario)
        }
    }

    fun getUsuarioById(id: String, authentication: Authentication): Usuario {

        val usuario = usuarioRepository.findById(id.toLong())
            .orElseThrow { ChangeSetPersister.NotFoundException() }

        // Verificar que el usuario autenticado sea el mismo que el del recurso solicitado
        validarUsuario(usuario, authentication)

        return usuario
    }

    fun getAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun updateUsuario(id: String, usuario: Usuario, authentication: Authentication): Usuario? {
        val userExist = getUsuarioById(id, authentication)
        validarUsuario(userExist, authentication)
        userExist.apply {
            username = usuario.username
            password = usuario.password
            roles = usuario.roles
        }
        usuarioRepository.save(userExist)
        return userExist
    }

    fun deleteUsuario(id: String, authentication: Authentication) : Usuario {
        val userDelete = getUsuarioById(id, authentication)
        validarUsuario(userDelete, authentication)
        usuarioRepository.delete(userDelete)
        return userDelete
    }
}