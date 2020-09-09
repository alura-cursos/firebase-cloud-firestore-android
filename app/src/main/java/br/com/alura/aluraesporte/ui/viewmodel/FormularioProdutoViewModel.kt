package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.repository.ProdutoRepository

class FormularioProdutoViewModel(private val repository: ProdutoRepository) : ViewModel() {

    fun salva(produto: Produto): LiveData<Boolean> = repository.salva(produto)

}
