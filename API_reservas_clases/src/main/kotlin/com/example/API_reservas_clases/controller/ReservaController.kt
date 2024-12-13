package com.example.API_reservas_clases.controller

import com.example.API_reservas_clases.model.Reserva
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservas")
class ReservaController {

    @PostMapping("/registrar")
    fun registerReserva(
        @RequestBody newReserva: Reserva
    ) : String {

        return "Reserva ${newReserva.id} registrada"
    }

    @GetMapping("/{id}")
    fun getReserva(@PathVariable id: String): String {
        return "Reserva $id"
    }

    /*
    @GetMapping("/{id}")
    fun getTodos(authentication: Authentication): String {
        return "Tipo de gasto ${authentication.name}"
    }
     */

    @DeleteMapping("/eliminar/{id}")
    fun eliminar(@PathVariable id: String): String {
        return "Reserva $id eliminada"
    }
}