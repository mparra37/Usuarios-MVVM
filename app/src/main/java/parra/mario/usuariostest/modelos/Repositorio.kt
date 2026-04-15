package parra.mario.usuariostest.modelos

class Repositorio{


    private val usuarios = mutableListOf(
            Usuario(1, "Mario", "mparra37@gmail.com", 30),
            Usuario(2, "Luis", "ejemplo2@gmail.com", 20),
            Usuario(3, "Jesus", "ejemplo3@gmail.com", 22),
            Usuario(4, "Jazmin", "ejemplo4@gmail.com", 21)
                    )
    fun getUsuarios(): List<Usuario>{
         return usuarios.toList()
    }

    fun agregarUsuario(usuario: Usuario){
        usuarios.add(usuario)
    }
}
