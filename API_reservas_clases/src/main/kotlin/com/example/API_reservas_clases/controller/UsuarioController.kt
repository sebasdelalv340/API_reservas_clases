package com.example.API_reservas_clases.controller

import com.example.API_reservas_clases.model.Usuario
import com.example.API_reservas_clases.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @PostMapping("/alta")
    fun altaUsuario(
        @RequestBody newUsuario: Usuario
    ) : ResponseEntity<Usuario?>? {

        usuarioService.registrarUsuario(newUsuario)

        return ResponseEntity(newUsuario, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario) : String {
        return "Usuario ${usuario.username} logueado"
    }
}

