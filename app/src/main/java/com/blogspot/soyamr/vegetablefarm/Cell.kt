package com.blogspot.soyamr.vegetablefarm

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class Cell {

    val state: MutableLiveData<State> = MutableLiveData(State.Empty)
    var job: Job? = null


    fun plant() {
        job?.cancel()
        state.value = State.Planted
        job = GlobalScope.launch(Dispatchers.Main) {
            delay(500)
            state.value = State.Green
            delay(500)
            state.value = State.Immature
            delay(500)
            state.value = State.Mature
            delay(500)
            state.value = State.Overgrow
        }
    }


    enum class State(val color: Int) {
        Empty(Color.WHITE),
        Planted(Color.BLACK),
        Green(Color.GREEN),
        Immature(Color.YELLOW),
        Mature(Color.RED),
        Overgrow(Color.GRAY);
    }
}