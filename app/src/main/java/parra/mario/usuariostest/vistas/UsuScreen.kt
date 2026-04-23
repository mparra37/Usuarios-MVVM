package parra.mario.usuariostest.vistas

import android.app.AlertDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import parra.mario.usuariostest.modelos.Usuario
import parra.mario.usuariostest.viewmodels.UsuarioViewModel
import kotlin.contracts.contract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioScreen(viewModel: UsuarioViewModel){

    val usuarios = viewModel.usuarios.value

    var mostrarDialogo by remember {mutableStateOf(false)  }

    var usuarioAEditar by remember { mutableStateOf<Usuario?>(null) }

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
                UsuarioCard(usuario, onLongClick = {
                    usuarioAEditar = usuario
                })
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

    }

    if(mostrarDialogo){
        AgregarUsuarioDialog(
            onDismiss = { mostrarDialogo = false },
            onConfirm = { nombre, correo, edad, uri ->
                viewModel.agregaUsuario(nombre, correo, edad, uri)
                mostrarDialogo = false
            }
        )
    }

    usuarioAEditar?.let { usuario ->
        EditarUsuarioDialog(
            usuario= usuario,
            onDismiss = { usuarioAEditar = null},
            onConfirm = { id, nombre, correo, edad, uri ->
                viewModel.editarUsuario(id, nombre, correo, edad, uri)
                usuarioAEditar = null
            }
        )
    }
}

@Composable
fun UsuarioCard(usuario: Usuario, onLongClick: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column (modifier = Modifier.padding(16.dp)
            ) {

            if(usuario.fotoUri != null){
                AsyncImage(
                    model = usuario.fotoUri,
                    contentDescription = "Foto",
                    modifier = Modifier.size(48.dp)
                )
            }else{
                    Image(
                    painter = painterResource(usuario.foto),
                   contentDescription = "Avatar",
                    modifier = Modifier.size(48.dp)
                )
            }


            Text(text = usuario.nombre)
            Text(text = usuario.correo)
            Text(text = usuario.edad.toString())


        }
    }
}

@Composable
fun AgregarUsuarioDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int, String?) -> Unit
){
    var foto by remember { mutableStateOf<Uri?>(null) }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract =    ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        foto  = uri

    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Nuevo Usuario")},
        text = {
            Column {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable{
                            launcher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ){
                    if(foto != null){
                        AsyncImage(
                            model = foto,
                            contentDescription = "Foto selección",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Elegir Foto",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Toca para elegir foto", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
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
                            onConfirm(nombre, correo, edadInt, foto?.toString())
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

@Composable
fun EditarUsuarioDialog(
    usuario: Usuario,
    onDismiss: () -> Unit,
    onConfirm: (Int, String, String, Int, String?) -> Unit
){
    var foto by remember { mutableStateOf<Uri?>(usuario.fotoUri?.let {Uri.parse(it)}) }
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var correo by remember { mutableStateOf(usuario.correo) }
    var edad by remember { mutableStateOf(usuario.edad.toString()) }

    val launcher = rememberLauncherForActivityResult(
        contract =    ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        foto  = uri

    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Editar Usuario")},
        text = {
            Column {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable{
                            launcher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ){
                    if(foto != null){
                        AsyncImage(
                            model = foto,
                            contentDescription = "Foto selección",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Elegir Foto",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Toca para elegir foto", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
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
                        onConfirm(usuario.id, nombre, correo, edadInt, foto?.toString())
                    }
                }
            ) {
                Text("Editar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }


    )
}