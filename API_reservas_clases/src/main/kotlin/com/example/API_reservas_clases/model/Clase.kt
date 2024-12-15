package com.example.API_reservas_clases.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "clases")
data class Clase(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var nombre: String,

    @Column(nullable = false)
    var descripcion: String,

    @Column(nullable = false)
    var aforo: Int,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var fecha_clase: LocalDateTime,

    @OneToMany(mappedBy = "clase", cascade = [CascadeType.ALL])
    val reservas: MutableList<Reserva> = mutableListOf()
)