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

    @PostMapping("/register")
    fun registerUsuario(
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
    fun getUsuarioById(@PathVariable id: String, authentication: Authentication): ResponseEntity<Usuario> {
        val usuario = usuarioService.getUsuarioById(id, authentication)

        return ResponseEntity(usuario, HttpStatus.OK)
    }

    @GetMapping("/getAll")
    fun getAll() : ResponseEntity<List<Usuario>>? {
        val lista = usuarioService.getAll()
        return ResponseEntity(lista, HttpStatus.OK)
    }


    @PutMapping("/update/{id}")
    fun updateUsuario(@PathVariable id: String?, @RequestBody usuario: Usuario?, authentication: Authentication) : ResponseEntity<Any>? {

        val usuarioActualizado = usuario?.let {
            if (id != null) {
                usuarioService.updateUsuario(id, it, authentication)
            }
        }

        return ResponseEntity(mapOf("message" to "Usuario actualizado con éxito", "usuario" to usuarioActualizado), HttpStatus.OK)
    }


    @DeleteMapping("/delete/{id}")
    fun deleteUsuario(@PathVariable id: String?, authentication: Authentication) : ResponseEntity<Any>? {

        val userEliminado = if (id != null) {
            usuarioService.deleteUsuario(id, authentication)
        } else {
            throw IllegalArgumentException("El ID $id no es válido")
        }

        return ResponseEntity(mapOf("message" to "Usuario eliminado con éxito", "usuario" to userEliminado), HttpStatus.OK)
    }
}

