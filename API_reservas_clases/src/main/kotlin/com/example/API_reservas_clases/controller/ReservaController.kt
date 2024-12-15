package com.example.API_reservas_clases.controller

import com.example.API_reservas_clases.model.Reserva
import com.example.API_reservas_clases.service.ReservaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservas")
class ReservaController {

    @Autowired
    private lateinit var reservaService: ReservaService

    @PostMapping("/register")
    fun registerReserva(@RequestBody newReserva: Reserva, authentication: Authentication): ResponseEntity<String> {
        val reservaGuardada = reservaService.registerReserva(newReserva, authentication)
        return ResponseEntity("Reserva ${reservaGuardada.id} registrada", HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getReserva(@PathVariable id: String, authentication: Authentication): ResponseEntity<Reserva> {
        val reserva = reservaService.getReservaById(id, authentication)

        return ResponseEntity(reserva, HttpStatus.OK)
    }


    @GetMapping("/getAll")
    fun getAll(): ResponseEntity<List<Reserva>> {
        val lista = reservaService.getAll()
        return ResponseEntity(lista, HttpStatus.OK)
    }

    @PutMapping("/update/{id}")
    fun updateReserva(@PathVariable id: String?, @RequestBody reserva: Reserva?, authentication: Authentication) : ResponseEntity<Any>? {
        val reservaActualizada = reserva?.let {
            if (id != null) {
                reservaService.updateReserva(id, it, authentication)
            }
        }

        return ResponseEntity(mapOf("message" to "Reserva actualizada con éxito", "reserva" to reservaActualizada), HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteReserva(@PathVariable id: String?, authentication: Authentication): ResponseEntity<Any>?{
        val reservaEliminada = if (id != null) {
            reservaService.deleteReserva(id, authentication)
        } else {
            throw IllegalArgumentException("El ID $id no es válido")
        }

        return ResponseEntity(mapOf("message" to "Reserva eliminada con éxito", "usuario" to reservaEliminada), HttpStatus.OK)
    }
}