package com.example.API_reservas_clases.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reservas")
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "clase_id")
    var clase: Clase? = null,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    var usuario: Usuario? = null,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var fecha_creacion: LocalDateTime = LocalDateTime.now()
) {
}