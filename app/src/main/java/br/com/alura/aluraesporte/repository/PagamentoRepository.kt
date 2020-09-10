package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import br.com.alura.aluraesporte.model.Pagamento

class PagamentoRepository() {

    fun salva(pagamento: Pagamento): LiveData<Resource<Long>> {
        TODO("Não foi implementa a inserção de pagamento")
    }

    fun todos(): LiveData<List<Pagamento>> = TODO("Não foi implementada a busca de pagamentos")

}
