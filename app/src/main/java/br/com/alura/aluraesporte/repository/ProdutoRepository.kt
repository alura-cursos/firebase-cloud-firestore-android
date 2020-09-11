package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.model.Produto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.math.BigDecimal
import java.math.RoundingMode

private const val COLECAO_FIRESTORE_PRODUTOS = "produtos"

class ProdutoRepository(
    private val firestore: FirebaseFirestore
) {

    fun buscaPorId(id: String): LiveData<Produto> = MutableLiveData<Produto>().apply {
        firestore.collection(COLECAO_FIRESTORE_PRODUTOS)
            .document(id)
            .addSnapshotListener { s, _ ->
                s?.let { documento ->
                    converteParaProduto(documento)
                        ?.let { produto ->
                            value = produto
                        }
                }
            }
    }

    fun salva(produto: Produto): LiveData<Boolean> = MutableLiveData<Boolean>().apply {
        val produtoDocumento = ProdutoDocumento(
            nome = produto.nome,
            preco = produto.preco.toDouble()
        )

        val colecao = firestore.collection(COLECAO_FIRESTORE_PRODUTOS)
        val documento = produto.id?.let { id ->
            colecao.document(id)
        } ?: colecao.document()

        documento.set(produtoDocumento)

        value = true
    }

    fun buscaTodos(): LiveData<List<Produto>> = MutableLiveData<List<Produto>>().apply {
        firestore.collection(COLECAO_FIRESTORE_PRODUTOS)
            .addSnapshotListener { s, _ ->
                s?.let { snapshot ->
                    val produtos: List<Produto> = snapshot.documents
                        .mapNotNull { documento ->
                            converteParaProduto(documento)
                        }
                    value = produtos
                }
            }
    }

    fun remove(produtoId: String) : LiveData<Boolean> = MutableLiveData<Boolean>().apply {
        firestore.collection(COLECAO_FIRESTORE_PRODUTOS)
            .document(produtoId)
            .delete()

        value = true
    }

    private fun converteParaProduto(documento: DocumentSnapshot) : Produto? =
        documento.toObject<ProdutoDocumento>()?.paraProduto(documento.id)

    private class ProdutoDocumento(
        val nome: String = "",
        val preco: Double = 0.0
    ) {
        fun paraProduto(id: String): Produto = Produto(
            id = id,
            nome = nome,
            preco = BigDecimal(preco).setScale(2, RoundingMode.HALF_EVEN)
        )
    }

}
