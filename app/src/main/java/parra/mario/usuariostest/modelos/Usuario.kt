package parra.mario.usuariostest.modelos

import androidx.core.R

data class Usuario(val id: Int, var nombre: String, var correo: String,
    var edad: Int, var foto: Int, var fotoUri: String? = null)

