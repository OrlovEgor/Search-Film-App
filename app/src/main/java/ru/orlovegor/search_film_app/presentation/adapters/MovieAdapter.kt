package ru.orlovegor.search_film_app.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.ItemMovieBinding
import ru.orlovegor.search_film_app.presentation.models.Movie

class MovieAdapter(
    context: Context,
    private val onItemClick: (movieId: Long) -> Unit,
    private val onCheckFavoriteState: (movie: Movie, isFavorite: Boolean) -> Unit
) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffItemCallback()) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.animation =
            loadAnimation(holder.itemView.context, R.anim.animation_alpha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            layoutInflater.inflate(R.layout.item_movie, parent, false),
            onItemClick, onCheckFavoriteState
        )
    }

    class MovieViewHolder(
        itemView: View,
        onItemClick: (movieId: Long) -> Unit,
        onCheckFavoriteState: (movie: Movie, isFavorite: Boolean) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding by viewBinding(ItemMovieBinding::bind)
        private var movieId: Long? = null
        private var sendMovie: Movie? = null

        init {
            binding.itemMovieFavoriteCheckbox.setOnClickListener {
                if (binding.itemMovieFavoriteCheckbox.isChecked) {
                    sendMovie?.isFavorite = true
                    sendMovie?.let { movie -> onCheckFavoriteState(movie, true) }
                } else {
                    sendMovie?.isFavorite = false
                    sendMovie?.let { movie -> onCheckFavoriteState(movie, false) }
                }
            }

            itemView.setOnClickListener {
                movieId?.let(onItemClick)
            }
        }

        fun bind(movie: Movie) {
            with(binding) {
                movieId = movie.id
                sendMovie = movie
                itemMovieTitleText.text = movie.title
                itemMovieReleaseDateText.text = movie.releaseDate
                itemMovieSloganText.text = movie.shortDescription
                itemMovieRatingText.text = movie.rating.toString()
                itemMovieFavoriteCheckbox.isChecked = movie.isFavorite
                Glide.with(itemView)
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.ic_picture_40)
                    .error(R.drawable.ic_error_40)
                    .into(itemMovieImageView)
            }
        }
    }

    private class MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }
}