// DetallePeliculaActivity.kt
package com.example.cinegs

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinegs.data.Model.Movie
import com.example.cinegs.ui.theme.CIneGSTheme

class DetallesPeliActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie = obtenerDetallesPelicula()

        setContent {
            CIneGSTheme {
                // Muestra los detalles de la película
                DetallePeliculaScreen(movie)
            }
        }
    }

    private fun obtenerDetallesPelicula(): Movie {
        return Movie(
            title = "Nombre de la Película",
            overview = "Descripción de la película.",
            posterPath = "ruta/del/poster.jpg"
        )
    }
}

@Composable
fun DetallePeliculaScreen(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        PosterImagen(posterPath = movie.posterPath)
        DetalleBasico(movie = movie)
    }
}

@Composable
fun PosterImagen(posterPath: String) {

    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(shape = MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DetalleBasico(movie: Movie) {
    // Muestra información básica de la película
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}
    @Composable
    fun DetallePeliculaScreen(movie: Movie, onBackClick: () -> Unit) {

        val context = LocalContext.current

        // Muestra los detalles de la película
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Botón de retroceso
            IconButton(onClick = { onBackClick.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Text(
                text = stringResource(id = R.string.movie_details_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )


            Button(
                onClick = {

                    setLocale(context, Locale("en"))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Switch to English")
            }


            Button(
                onClick = {
                    // Cambia el idioma a español
                    setLocale(context, Locale("es"))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Cambiar a Español")
            }
        }
    }

    /**
     * Función para cambiar el idioma de la aplicación.
     */
    fun setLocale(context: Context, locale: Locale) {
        val configuration = Configuration(context.resources.configuration)

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)


        val intent = Intent(context, DetallesPeliActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

