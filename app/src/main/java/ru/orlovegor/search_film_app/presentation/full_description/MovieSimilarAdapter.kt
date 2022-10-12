package ru.orlovegor.search_film_app.presentation.full_description

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.orlovegor.search_film_app.R
import ru.orlovegor.search_film_app.databinding.ItemSimilarMovieBinding
import ru.orlovegor.search_film_app.presentation.models.SimilarMovie

class MovieSimilarAdapter(
    private val onItemClick: (movieId: Long) -> Unit
) :
    ListAdapter<SimilarMovie, MovieSimilarAdapter.SimilarMovieViewHolder>(MovieDiffItemCallback()) {

    private class MovieDiffItemCallback : DiffUtil.ItemCallback<SimilarMovie>() {
        override fun areItemsTheSame(oldItem: SimilarMovie, newItem: SimilarMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SimilarMovie, newItem: SimilarMovie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        val binding = ItemSimilarMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SimilarMovieViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation_alpha)
    }

    class SimilarMovieViewHolder(
        private val binding: ItemSimilarMovieBinding,
        onItemClick: (movieId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var movieId: Long? = null

        init {
            itemView.setOnClickListener {
                movieId?.let { it1 -> onItemClick(it1) }
            }
        }

        fun bind(similarMovie: SimilarMovie) {
            movieId = similarMovie.id
            Glide.with(itemView)
                .load(similarMovie.imageUrl)
                .placeholder(R.drawable.ic_picture_40)
                .error(R.drawable.ic_error_40)
                .into(binding.posterImage)

        }
    }
}