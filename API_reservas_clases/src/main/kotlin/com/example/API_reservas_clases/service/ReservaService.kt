package com.example.API_reservas_clases.service

import com.example.API_reservas_clases.model.Reserva
import com.example.API_reservas_clases.repository.ReservaRepository
import com.example.API_reservas_clases.util.validarAforo
import com.example.API_reservas_clases.util.validarFecha
import com.example.API_reservas_clases.util.validarUsuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class ReservaService {

    @Autowired
    private lateinit var reservaRepository: ReservaRepository


    // Registrar una nueva reserva
    fun registerReserva(newReserva: Reserva, authentication: Authentication): Reserva {

        newReserva.usuario?.let { validarUsuario(it, authentication) }

        // Agregar lógica de validación si es necesario, como comprobar si la clase tiene aforo disponible
        val clase =  newReserva.clase
        val aforoDisponible = 20
        val reservasParaClase = clase?.let { reservaRepository.countByClase(it) }
        if (reservasParaClase != null) {
            if (!validarAforo(reservasParaClase, aforoDisponible)) {
                throw IllegalArgumentException("La clase ${clase.nombre} ya ha alcanzado su aforo máximo.")
            }
        }
        return reservaRepository.save(newReserva)
    }

    // Obtener una reserva por ID
    fun getReservaById(id: String, authentication: Authentication): Reserva {
        val reserva = reservaRepository.findById(id.toLong())
            .orElseThrow { ChangeSetPersister.NotFoundException() }

        reserva.usuario?.let { validarUsuario(it, authentication) }

        return reserva
    }

    // Obtener todas las reservas
    fun getAll(): List<Reserva> {
        return reservaRepository.findAll()
    }

    // Actualizar una reserva
    fun updateReserva(id: String, reserva: Reserva, authentication: Authentication): Reserva {
        val existingReserva = getReservaById(id, authentication)
        existingReserva.apply {
            // Aquí puedes actualizar solo los campos necesarios
            this.clase = reserva.clase
            this.usuario = reserva.usuario
            this.fecha_creacion = reserva.fecha_creacion
        }
        return reservaRepository.save(existingReserva)
    }

    // Eliminar una reserva
    fun deleteReserva(id: String, authentication: Authentication): Reserva {
        val reservaDelete = getReservaById(id, authentication)
        reservaDelete.usuario?.let { validarUsuario(it, authentication) }
        if (validarFecha(reservaDelete)){
            reservaRepository.delete(reservaDelete)
        }
        return reservaDelete
    }
}
