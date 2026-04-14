package parra.mario.usuariostest.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import parra.mario.usuariostest.modelos.*
import kotlin.collections.emptyList

class UsuarioViewModel(val repo: Repositorio) : ViewModel() {

    private val _usuarios = mutableStateOf<List<Usuario>>(emptyList())
    val usuarios: State<List<Usuario>> = _usuarios


    init {
        getUsuarios()
    }

    private fun getUsuarios(){
        _usuarios.value = repo.getUsuarios()

    }
}