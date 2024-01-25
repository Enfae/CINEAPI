package com.example.cinegs.data.Model

data class RemoteResule(
     val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    val movies: List<Movie>
        get() = results.map { it.toMovie() }
}

data class Result(

    val title: String,
    val overview: String,
    val poster_path: String,
) {

    fun toMovie(): Movie {
        return Movie(
            title = title,
            overview = overview,
            posterPath = poster_path
        )
    }
}

data class Movie(

    val title: String,
    val overview: String,
    val posterPath: String,

)
