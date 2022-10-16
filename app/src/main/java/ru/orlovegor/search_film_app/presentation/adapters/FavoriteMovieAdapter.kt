package ru.orlovegor.search_film_app.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.ItemMovieBinding
import ru.orlovegor.search_film_app.presentation.models.Movie


class FavoriteMovieAdapter() :
    ListAdapter<Movie, FavoriteMovieAdapter.MovieViewHolder>(MovieDiffItemCallback()) {

    private class MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }

   override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation_alpha)
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {

            with(binding) {
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

}