package com.example.API_reservas_clases.service

import com.example.API_reservas_clases.model.Usuario
import com.example.API_reservas_clases.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
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
        }
        return usuarioRepository.save(usuario)
    }
}