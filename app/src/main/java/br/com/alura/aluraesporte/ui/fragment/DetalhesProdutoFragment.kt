package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.DetalhesProdutoViewModel
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import kotlinx.android.synthetic.main.detalhes_produto.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetalhesProdutoFragment : BaseFragment() {

    private val argumentos by navArgs<DetalhesProdutoFragmentArgs>()
    private val produtoId by lazy {
        argumentos.produtoId
    }
    private val viewModel: DetalhesProdutoViewModel by viewModel { parametersOf(produtoId) }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val controlador by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.detalhes_produto,
            container,
            false
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detalhes_produto, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_altera -> vaiParaFormulario()
            R.id.menu_detalhes_produto_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun remove() {
        viewModel.remove().observe(viewLifecycleOwner) {
            controlador.popBackStack()
        }
    }

    private fun vaiParaFormulario() {
        DetalhesProdutoFragmentDirections
            .acaoDetalhesProdutoParaFormularioProduto(produtoId)
            .let(controlador::navigate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais(appBar = true)
        buscaProduto()
    }

    private fun buscaProduto() {
        viewModel.produtoEncontrado.observe(viewLifecycleOwner, {
            it?.let { produto ->
                detalhes_produto_nome.text = produto.nome
                detalhes_produto_preco.text = produto.preco.formatParaMoedaBrasileira()
            }
        })
    }

}