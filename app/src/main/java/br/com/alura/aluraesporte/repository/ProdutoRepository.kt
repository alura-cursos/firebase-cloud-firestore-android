package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.math.BigDecimal

private const val COLECAO_FIRESTORE_PRODUTOS = "produtos"

class ProdutoRepository(
    private val firestore: FirebaseFirestore
) {

    fun buscaPorId(id: Long): LiveData<Produto> = TODO("não foi implementada a busca do produto por id")

    fun salva(produto: Produto): LiveData<Boolean> = MutableLiveData<Boolean>().apply {
        val produtoDocumento = ProdutoDocumento(
            nome = produto.nome,
            preco = produto.preco.toDouble()
        )

        val documento = firestore.collection(COLECAO_FIRESTORE_PRODUTOS)
            .document()

        documento.set(produtoDocumento)

        value = true
    }

    fun buscaTodos(): LiveData<List<Produto>> = MutableLiveData<List<Produto>>().apply {
        firestore.collection(COLECAO_FIRESTORE_PRODUTOS)
            .addSnapshotListener { s, _ ->
                s?.let { snapshot ->
                    val produtos: List<Produto> = snapshot.documents
                        .mapNotNull { documento ->
                            documento.toObject<ProdutoDocumento>()?.paraProduto()
                        }
                    value = produtos
                }
            }
    }

    private class ProdutoDocumento(
        val nome: String = "",
        val preco: Double = 0.0
    ) {
        fun paraProduto(): Produto = Produto(
            nome = nome,
            preco = BigDecimal(preco)
        )
    }

}
