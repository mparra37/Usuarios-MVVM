package parra.mario.usuariostest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import parra.mario.usuariostest.modelos.Repositorio
import parra.mario.usuariostest.ui.theme.UsuariosTestTheme
import parra.mario.usuariostest.viewmodels.UsuarioViewModel
import parra.mario.usuariostest.viewmodels.UsuarioViewModelFactory
import parra.mario.usuariostest.vistas.UsuarioScreen

class MainActivity : ComponentActivity() {

    private val viewModel: UsuarioViewModel by viewModels {
        UsuarioViewModelFactory(Repositorio())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            UsuarioScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

