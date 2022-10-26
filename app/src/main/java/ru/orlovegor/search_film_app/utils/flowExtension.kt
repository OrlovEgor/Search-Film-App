package ru.orlovegor.search_film_app.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun RangeSlider.onTouchListenerFlow(): Flow<String> {
    return callbackFlow {
        val onTouchListener = object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val value = "${slider.values[0]}-${slider.values[1]}"
                trySendBlocking(value).onFailure { Log.d("TAG", "error onTouchListenerFlow") }
            }

        }
        this@onTouchListenerFlow.addOnSliderTouchListener(onTouchListener)
        awaitClose {
            this@onTouchListenerFlow.removeOnSliderTouchListener(onTouchListener)
        }
    }
}

fun SearchView.queryTextListenerFlow(): Flow<String> {
    return callbackFlow {
        val listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                trySendBlocking(
                    query.orEmpty()
                ).onFailure { Log.d("TAG", "error QueryTextListenerFlow") }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        }
        this@queryTextListenerFlow.setOnQueryTextListener(listener)
        awaitClose {
            Log.d("TAG", "close QueryTextListenerFlow")
        }
    }
}


fun AutoCompleteTextView.valueChangedFlow(): Flow<String> {
    return callbackFlow {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                trySendBlocking(
                    p0?.toString().orEmpty()
                ).onFailure { Log.d("TAG", "error textChangedFlow") }
            }
        }
        this@valueChangedFlow.addTextChangedListener(textChangedListener)

        awaitClose {
            this@valueChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}