package ru.orlovegor.search_film_app.presentation.full_description

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.ItemFullDescriptionBinding
import ru.orlovegor.search_film_app.presentation.models.Movie

class FullDescriptionMovieAdapter(
    private val onItemClickNested: (movieId: Long) -> Unit
) : RecyclerView.Adapter<FullDescriptionMovieAdapter.FullDescriptionMovieViewHolder>() {

    private var movies = listOf<Movie>()

    val similarMovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieSimilarAdapter(onItemClick = onItemClickNested)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FullDescriptionMovieViewHolder {
        val binding =
            ItemFullDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FullDescriptionMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FullDescriptionMovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class FullDescriptionMovieViewHolder(
        private val binding: ItemFullDescriptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                tittleText.text = movie.title
                descriptionText.text = movie.description
                ratingText.text = movie.rating.toString()
                ageRestrictionsText.text = movie.ageRestriction
                Glide.with(binding.root)
                    .load(movie.posterUrl)
                    .error(R.drawable.ic_error_40)
                    .placeholder(R.drawable.ic_picture_40)
                    .into(binding.posterImage)

                initList(similarMovieAdapter)
                similarMovieAdapter.submitList(movie.similarMovie)
            }
        }

        private fun initList(similarMovieAdapter: MovieSimilarAdapter) {

            with(binding.similarMovieRecycler) {
                this.adapter = similarMovieAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}