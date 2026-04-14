package parra.mario.usuariostest.modelos

data class Usuario(val id: Int, var nombre: String, var correo: String,
    var edad: Int)

class Repositorio{

    fun getUsuarios(): List<Usuario>{
        return listOf(
            Usuario(1, "Mario", "mparra37@gmail.com", 30),
            Usuario(2, "Luis", "ejemplo2@gmail.com", 20),
            Usuario(3, "Jesus", "ejemplo3@gmail.com", 22),
            Usuario(4, "Jazmin", "ejemplo4@gmail.com", 21)
        )
    }
}
