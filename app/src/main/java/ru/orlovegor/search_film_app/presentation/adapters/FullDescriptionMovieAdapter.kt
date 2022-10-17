package ru.orlovegor.search_film_app.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.ItemFullDescriptionBinding
import ru.orlovegor.search_film_app.presentation.models.Movie

class FullDescriptionMovieAdapter(
    private val onItemClickNested: (movieId: Long) -> Unit,
    private val isFavoriteClick: (movie:Movie, isFavorite: Boolean ) -> Unit,
    private val context: Context
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
        return FullDescriptionMovieViewHolder(binding,isFavoriteClick)
    }

    override fun onBindViewHolder(holder: FullDescriptionMovieViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation_alpha)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class FullDescriptionMovieViewHolder(
        private val binding: ItemFullDescriptionBinding,
        isFavoriteClick: (movie: Movie, isFavorite: Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var sendMovie: Movie? = null

        init {

            binding.itemMovieFavoriteCheckbox.setOnClickListener {
                if (binding.itemMovieFavoriteCheckbox.isChecked) {
                    sendMovie?.isFavorite = true
                    sendMovie?.let { movie -> isFavoriteClick(movie, true) }
                } else {
                    sendMovie?.isFavorite = false
                    sendMovie?.let { movie -> isFavoriteClick(movie, false) }
                }

                initList(similarMovieAdapter)
            }
        }

        fun bind(movie: Movie) {
            sendMovie = movie
            with(binding) {
                tittleText.text = movie.title
                descriptionText.text = movie.description
                ratingText.text = movie.rating.toString()
                ageRestrictionsText.text = movie.ageRestriction
                itemMovieFavoriteCheckbox.isChecked = movie.isFavorite
                Glide.with(binding.root)
                    .load(movie.posterUrl)
                    .error(R.drawable.ic_error_40)
                    .placeholder(R.drawable.ic_picture_40)
                    .into(binding.posterImage)

                similarMovieAdapter.submitList(movie.similarMovie)
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun initList(similarMovieAdapter: MovieSimilarAdapter) {
            val dividerDecorator = DividerItemDecoration(context, RecyclerView.HORIZONTAL)
            dividerDecorator.setDrawable(
                context.resources.getDrawable(
                    R.drawable.divider_white,
                    context.theme
                )
            )

            with(binding.similarMovieRecycler) {
                this.adapter = similarMovieAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(dividerDecorator)
            }
        }
    }
}