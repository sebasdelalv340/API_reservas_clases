package com.example.API_reservas_clases.controller

import com.example.API_reservas_clases.model.Clase
import com.example.API_reservas_clases.service.ClaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clases")
class ClaseController {

    @Autowired
    private lateinit var claseService: ClaseService

    // Obtener todas las clases (accesible por cualquier usuario autenticado)
    @GetMapping("/getAll")
    fun getAllClases(authentication: Authentication): ResponseEntity<List<Clase>> {
        val lista = claseService.getAll()
        return ResponseEntity(lista, HttpStatus.OK)
    }

    // Obtener una clase por ID (accesible por cualquier usuario autenticado)
    @GetMapping("/{id}")
    fun getClase(@PathVariable id: Long, authentication: Authentication): ResponseEntity<Clase> {

        val clase = claseService.getClaseById(id)
        return ResponseEntity(clase, HttpStatus.OK)
    }

    // Registrar una nueva clase (solo accesible por administradores)
    @PostMapping("/register")
    fun registerClase(@RequestBody newClase: Clase, authentication: Authentication): ResponseEntity<String> {

        val claseGuardada = claseService.registrarClase(newClase)
        return ResponseEntity("Clase ${claseGuardada.nombre} registrada con éxito", HttpStatus.CREATED)
    }

    // Actualizar una clase (solo accesible por administradores)
    @PutMapping("/update/{id}")
    fun updateClase(@PathVariable id: Long, @RequestBody clase: Clase, authentication: Authentication): ResponseEntity<Clase> {
        val claseActualizada = claseService.updateClase(id, clase)
        return ResponseEntity(claseActualizada, HttpStatus.OK)
    }

    // Eliminar una clase (solo accesible por administradores)
    @DeleteMapping("/delete/{id}")
    fun deleteClase(@PathVariable id: String?, authentication: Authentication): ResponseEntity<Any> {

        val claseEliminada = if (id != null) {
            claseService.deleteClase(id, authentication)
        } else {
            throw IllegalArgumentException("El ID $id no es válido")
        }

        return ResponseEntity(mapOf("message" to "Clase eliminada con éxito", "usuario" to claseEliminada), HttpStatus.OK)
    }
}
