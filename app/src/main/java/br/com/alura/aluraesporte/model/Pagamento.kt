package br.com.alura.aluraesporte.model

import java.math.BigDecimal

class Pagamento(
    val numeroCartao: Int,
    val dataValidade: String,
    val cvc: Int,
    val preco: BigDecimal,
    val produtoId: Long
)