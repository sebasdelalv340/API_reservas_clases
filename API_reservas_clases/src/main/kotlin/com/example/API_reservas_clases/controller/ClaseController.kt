package com.example.API_reservas_clases.controller

import com.example.API_reservas_clases.model.Clase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clases")
class ClaseController {

    @PostMapping("/register")
    fun registerClase(@RequestBody newClase: Clase): String {

        return "Tipo de gasto ${newClase.id} registrado"
    }

    @GetMapping("/{id}")
    fun getClase(@PathVariable id: String): String {
        return "Tipo de gasto $id"
    }

    @DeleteMapping("/eliminar/{id}")
    fun eliminar(@PathVariable id: String): String {
        return "Tipo de gasto $id eliminado"
    }
}