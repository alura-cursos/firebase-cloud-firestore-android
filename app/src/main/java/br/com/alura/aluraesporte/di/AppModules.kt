package br.com.alura.aluraesporte.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository
import br.com.alura.aluraesporte.repository.PagamentoRepository
import br.com.alura.aluraesporte.repository.ProdutoRepository
import br.com.alura.aluraesporte.ui.fragment.DetalhesProdutoFragment
import br.com.alura.aluraesporte.ui.fragment.ListaProdutosFragment
import br.com.alura.aluraesporte.ui.fragment.PagamentoFragment
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ListaPagamentosAdapter
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProdutosAdapter
import br.com.alura.aluraesporte.ui.viewmodel.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val daoModule = module {
    single<ProdutoRepository> { ProdutoRepository(get()) }
    single<PagamentoRepository> { PagamentoRepository() }
    single<FirebaseAuthRepository> { FirebaseAuthRepository(get()) }
    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val uiModule = module {
    factory<DetalhesProdutoFragment> { DetalhesProdutoFragment() }
    factory<ListaProdutosFragment> { ListaProdutosFragment() }
    factory<PagamentoFragment> { PagamentoFragment() }
    factory<ProdutosAdapter> { ProdutosAdapter(get()) }
    factory<ListaPagamentosAdapter> { ListaPagamentosAdapter(get()) }
}

val viewModelModule = module {
    viewModel<ProdutosViewModel> { ProdutosViewModel(get()) }
    viewModel<DetalhesProdutoViewModel> { (id: String) -> DetalhesProdutoViewModel(id, get()) }
    viewModel<PagamentoViewModel> { PagamentoViewModel(get(), get()) }
    viewModel<LoginViewModel> { LoginViewModel(get()) }
    viewModel<EstadoAppViewModel> { EstadoAppViewModel() }
    viewModel<CadastroUsuarioViewModel> { CadastroUsuarioViewModel(get()) }
    viewModel<MinhaContaViewModel> { MinhaContaViewModel(get()) }
    viewModel<FormularioProdutoViewModel> {FormularioProdutoViewModel(get())}
}

val firebaseModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }
}