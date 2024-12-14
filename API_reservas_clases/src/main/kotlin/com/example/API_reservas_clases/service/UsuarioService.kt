package com.example.API_reservas_clases.service

import com.example.API_reservas_clases.model.Usuario
import com.example.API_reservas_clases.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService : UserDetailsService {


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
            return usuarioRepository.save(usuario)
        }
    }

    fun getUsuarioById(id: String, authentication: Authentication): Usuario {
        val user = usuarioRepository.findById(id.toLong()).get()
        if (user.username == authentication.name) {
            return usuarioRepository.findById(id.toLong())
                .orElseThrow { IllegalArgumentException("Usuario con ID $id no encontrado.") }
        }
        return user
    }

    fun getAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun updateUsuario(id: String, usuario: Usuario, authentication: Authentication): Usuario? {
        val userExist = getUsuarioById(id, authentication)
        if (userExist.username == authentication.name) {
            userExist.apply {
                username = usuario.username
                password = usuario.password
                roles = usuario.roles
            }

            return usuarioRepository.save(userExist)
        } else {
            throw IllegalArgumentException("Usuario con nombre ${authentication.name} no tiene permiso para actualizar este usuario.")
        }


    }

    fun deleteUsuario(id: String, authentication: Authentication) : Usuario {
        val username = authentication.name

        val userDelete = usuarioRepository.findById(id.toLong())
            .orElseThrow { IllegalArgumentException("Usuario con ID $id no encontrado.") }

        if (userDelete.username != username) {
            throw AccessDeniedException("No tienes permiso para eliminar este usuario.")
        }

        usuarioRepository.delete(userDelete)
        return userDelete

    }
}