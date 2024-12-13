package com.example.API_reservas_clases.repository

import com.example.API_reservas_clases.model.Clase
import org.springframework.data.jpa.repository.JpaRepository

interface ClaseRepository : JpaRepository<Clase, Long>{}