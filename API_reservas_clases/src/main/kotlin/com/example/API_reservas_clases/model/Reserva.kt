package com.example.API_reservas_clases.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "reservas")
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "clase_id")
    val clase: Clase? = null,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario? = null,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val fecha_creacion: LocalDate = LocalDate.now(),
) {
}