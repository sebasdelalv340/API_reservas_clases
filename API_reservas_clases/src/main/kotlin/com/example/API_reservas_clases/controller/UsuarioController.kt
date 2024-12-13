package com.example.API_reservas_clases.controller

import com.example.API_reservas_clases.model.Usuario
import com.example.API_reservas_clases.security.TokenService
import com.example.API_reservas_clases.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

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
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any>? {
        val authentication: Authentication = try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuario.username, usuario.password))
        } catch (e: AuthenticationException) {
            return ResponseEntity(usuario, HttpStatus.UNAUTHORIZED)
        }

        val token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }


    @GetMapping("/get/{id}")
    fun getUsuarioById(@PathVariable id: String): ResponseEntity<Usuario> {
        val usuario = usuarioService.getUsuarioById(id)

        return ResponseEntity(usuario, HttpStatus.OK)
    }


    @PutMapping("/update/{id}")
    fun updateUsuario(@PathVariable id: String?, @RequestBody usuario: Usuario?) : ResponseEntity<Usuario?>? {
        if (id != null && usuario != null) {
            usuarioService.updateUsuario(id, usuario)
        } else {
            throw IllegalArgumentException("Usuario ID invalid")
        }

        return ResponseEntity(usuario, HttpStatus.OK)
    }


    @DeleteMapping("/delete/{id}")
    fun deleteUsuario(@PathVariable id: String?, authentication: Authentication) : ResponseEntity<Usuario> {
        val user = id?.let { usuarioService.getUsuarioById(it) }
        if (id != null) {
            usuarioService.deleteUsuario(id, authentication)
        }

        return ResponseEntity(user, HttpStatus.OK)
    }
}

