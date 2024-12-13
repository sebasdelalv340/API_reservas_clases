package com.example.API_reservas_clases.repository

import com.example.API_reservas_clases.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsuarioRepository : JpaRepository<Usuario, Long> {

    // Implementar una derived query para obtener a un usuario por su nombre
    fun findByUsername(username: String?): Optional<Usuario>
}