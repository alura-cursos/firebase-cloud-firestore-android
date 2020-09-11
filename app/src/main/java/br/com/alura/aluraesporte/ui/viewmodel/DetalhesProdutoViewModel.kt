package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.ProdutoRepository

class DetalhesProdutoViewModel(
    private val produtoId: String,
    private val repository: ProdutoRepository
) : ViewModel() {

    val produtoEncontrado = repository.buscaPorId(produtoId)

    fun remove() : LiveData<Boolean> = repository.remove(produtoId)

}