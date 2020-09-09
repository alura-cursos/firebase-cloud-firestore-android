package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.snackBar
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import br.com.alura.aluraesporte.ui.viewmodel.FormularioProdutoViewModel
import kotlinx.android.synthetic.main.formulario_produto.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class FormularioProdutoFragment : BaseFragment() {

    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val viewModel: FormularioProdutoViewModel by viewModel()
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.formulario_produto,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais(
            appBar = true,
            bottomNavigation = false
        )
        formulario_produto_botao_salva.setOnClickListener {
            val nome = formulario_produto_campo_nome.editText?.text.toString()
            val preco = formulario_produto_campo_preco.editText?.text.toString()
            val produto = Produto(nome = nome, preco = BigDecimal(preco))
            viewModel.salva(produto).observe(viewLifecycleOwner) {
                it?.let {salvo ->
                    if(salvo) {
                        controlador.popBackStack()
                        return@observe
                    }
                    view.snackBar("Falha ao salvar produto")
                }
            }
        }
    }

}