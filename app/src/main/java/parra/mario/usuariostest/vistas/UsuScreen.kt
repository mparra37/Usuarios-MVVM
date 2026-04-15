package parra.mario.usuariostest.vistas

import android.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import parra.mario.usuariostest.modelos.Usuario
import parra.mario.usuariostest.viewmodels.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioScreen(viewModel: UsuarioViewModel){

    val usuarios = viewModel.usuarios.value

    var mostrarDialogo by remember {mutableStateOf(false)  }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Usuarios")})
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                mostrarDialogo = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            items(usuarios){ usuario ->
                UsuarioCard(usuario)
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

    }

    if(mostrarDialogo){
        AgregarUsuarioDialog(
            onDismiss = { mostrarDialogo = false },
            onConfirm = { nombre, correo, edad ->
                viewModel.agregaUsuario(nombre, correo, edad)
                mostrarDialogo = false
            }
        )
    }
}

@Composable
fun UsuarioCard(usuario: Usuario){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = usuario.nombre)
            Text(text = usuario.correo)
            Text(text = usuario.edad.toString())
        }
    }
}

@Composable
fun AgregarUsuarioDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int) -> Unit
){
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Nuevo Usuario")},
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it},
                    label = { Text("Nombre") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
               },
        confirmButton = {
                TextButton(
                    onClick = {
                        val edadInt = edad.toIntOrNull() ?: 0
                        if (nombre.isNotBlank() && correo.isNotBlank() && edadInt > 0) {
                            onConfirm(nombre, correo, edadInt)
                        }
                    }
                ) {
                    Text("Agregar")
                }
            },
        dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }


    )
}