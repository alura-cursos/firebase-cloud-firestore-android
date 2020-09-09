package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.database.dao.ProdutoDAO
import br.com.alura.aluraesporte.model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.math.BigDecimal

private const val TAG = "ProdutoRepository"

class ProdutoRepository(
    private val dao: ProdutoDAO,
    private val firestore: FirebaseFirestore
) {

    fun buscaTodos(): LiveData<List<Produto>> = dao.buscaTodos()

    fun buscaPorId(id: Long): LiveData<Produto> = dao.buscaPorId(id)

    fun salva(produto: Produto) : LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
//        val produtoMapeado = mapOf<String, Any>(
//            "nome" to produto.nome,
//            "preco" to produto.preco.toDouble()
//        )
        val produtoDocumento = ProdutoDocumento(
            nome = produto.nome,
            preco = produto.preco.toDouble()
        )

        firestore.collection("produtos")
            .add(produtoDocumento)
            .addOnSuccessListener {
                liveData.value = true
            }
            .addOnFailureListener {
                liveData.value = false
            }
        return liveData
    }

    fun buscaTodosFirestore(): LiveData<List<Produto>> {
        val liveData: MutableLiveData<List<Produto>> = MutableLiveData<List<Produto>>()
        firestore.collection("produtos")
            .get()
            .addOnSuccessListener {
                it?.let { snapshot ->
                    val produtos = mutableListOf<Produto>()
                    for (documento in snapshot.documents) {
                        Log.i(TAG, "onCreate: produto encontrado ${documento.data}")
//                        documento.data?.let { dados ->
//                            val nome: String = dados["nome"] as String
//                            val preco: Double = dados["preco"] as Double
//                            val produto = Produto(nome = nome, preco = BigDecimal(preco))
//                            produtos.add(produto)
//                        }
                        val produtoDocumento = documento.toObject<ProdutoDocumento>()
                        produtoDocumento?.let { produtoDocumentoNaoNulo ->
                            produtos.add(produtoDocumentoNaoNulo.paraProduto())
                        }
                    }
                    liveData.value = produtos
                }
            }
        return liveData
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
