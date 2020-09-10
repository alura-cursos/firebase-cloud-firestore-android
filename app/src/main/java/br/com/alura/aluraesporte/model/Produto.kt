package br.com.alura.aluraesporte.model

import java.math.BigDecimal

class Produto(
    val id: String? = null,
    val nome: String = "",
    val preco: BigDecimal = BigDecimal.ZERO
)
