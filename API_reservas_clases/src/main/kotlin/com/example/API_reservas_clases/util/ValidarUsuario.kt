package com.example.API_reservas_clases.util

import com.example.API_reservas_clases.model.Usuario
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication

fun validarUsuario(usuario: Usuario, authentication: Authentication) {
    // Verificar si el usuario autenticado es el propietario
    val usuarioAutenticado = authentication.name
    val esAdmin = authentication.authorities.any { it.authority == "ADMIN" }

    if (usuario.username != usuarioAutenticado || !esAdmin) {
        throw AccessDeniedException("No tienes permiso para realizar esta acción.")
    }
}