package parra.mario.usuariostest.modelos

import parra.mario.usuariostest.R

class Repositorio{


    private val usuarios = mutableListOf(
            Usuario(1, "Mario", "mparra37@gmail.com", 30,
                R.drawable.avatar),
            Usuario(2, "Luis", "ejemplo2@gmail.com", 20,
                R.drawable.radix_avatar),
            Usuario(3, "Jesus", "ejemplo3@gmail.com", 22,
                R.drawable.avatar),
            Usuario(4, "Jazmin", "ejemplo4@gmail.com", 21,
                R.drawable.radix_avatar)
                    )
    fun getUsuarios(): List<Usuario>{
         return usuarios.toList()
    }

    fun agregarUsuario(usuario: Usuario){
        usuarios.add(usuario)
    }
}
