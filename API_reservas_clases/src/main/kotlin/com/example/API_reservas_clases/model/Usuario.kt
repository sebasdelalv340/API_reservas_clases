package com.example.API_reservas_clases.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val username: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    val roles: String? = null,

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val reservas: List<Reserva> = mutableListOf()

) {
}