import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cinegs.R
import com.example.cinegs.data.Model.Movie
import com.example.cinegs.data.RetrofitService
import com.example.cinegs.data.RetrofitServiceFactory
import com.example.cinegs.ui.theme.CIneGSTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = RetrofitServiceFactory.makeRetrofitService()

        setContent {
            CIneGSTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TopMovieList(service)
                }
            }
        }
    }

    @Composable
    fun TopMovieList(service: RetrofitService) {
        var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
        var selectedMovie by remember { mutableStateOf<Movie?>(null) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            try {
                val result = service.listPopularMovies("58fa658e13f268d01f8c13bf0b921012", region = "US")
                movies = movies.reversed()
            } catch (e: Exception) {

            }
        }

        LazyColumn {
            items(movies) { movie ->
                MovieItem(movie) {
                    selectedMovie = movie
                }
            }
        }


        selectedMovie?.let { movie ->
            DetallePeliculaScreen(movie = movie) {
                selectedMovie = null
            }
        }
    }

    @Composable
    fun MovieItem(movie: Movie, onItemClick: () -> Unit) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onItemClick.invoke() }
        ) {
            Column {

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }

    @Composable
    fun DetallePeliculaScreen(movie: Movie, onBackClick: () -> Unit) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {

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
                text = movie.title,
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
        }
    }
}
