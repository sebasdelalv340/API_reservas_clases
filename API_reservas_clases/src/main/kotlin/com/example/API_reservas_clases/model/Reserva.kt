package com.example.API_reservas_clases.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reservas")
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id")
    @JsonBackReference
    var clase: Clase?,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    var usuario: Usuario?,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var fecha_creacion: LocalDateTime = LocalDateTime.now()
) {

}