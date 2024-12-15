package com.example.API_reservas_clases.service

import com.example.API_reservas_clases.model.Clase
import com.example.API_reservas_clases.repository.ClaseRepository
import com.example.API_reservas_clases.repository.ReservaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class ClaseService {

    @Autowired
    private lateinit var claseRepository: ClaseRepository

    @Autowired
    private lateinit var reservaRepository: ReservaRepository

    // Obtener todas las clases
    fun getAll(): List<Clase> {
        return claseRepository.findAll()
    }

    // Obtener una clase por ID
    fun getClaseById(id: Long): Clase {
        return claseRepository.findById(id).orElseThrow { ChangeSetPersister.NotFoundException() }
    }

    // Registrar una nueva clase (solo debe ser accesible por ADMIN)
    fun registrarClase(newClase: Clase): Clase {
        // Aquí podemos agregar lógica para validar que no haya otra clase con el mismo nombre o algo similar
        return claseRepository.save(newClase)
    }

    // Actualizar una clase (solo debe ser accesible por ADMIN)
    fun updateClase(id: Long, clase: Clase): Clase {
        val existingClase = claseRepository.findById(id).orElseThrow { IllegalArgumentException("Clase con ID $id no encontrada.") }
        // Actualizamos los campos de la clase existente con los nuevos datos
        existingClase.apply {
            this.nombre = clase.nombre
            this.descripcion = clase.descripcion
            this.aforo = clase.aforo
            this.fecha_clase = clase.fecha_clase
        }
        return claseRepository.save(existingClase)
    }

    // Eliminar una clase (solo debe ser accesible por ADMIN)
    fun deleteClase(id: String, authentication: Authentication) {
        val clase = claseRepository
            .findById(id.toLong())
            .orElseThrow { IllegalArgumentException("Clase con ID $id no encontrada.") }
        claseRepository.delete(clase)
    }
}