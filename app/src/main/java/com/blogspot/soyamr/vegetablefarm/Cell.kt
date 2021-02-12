package com.blogspot.soyamr.vegetablefarm

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class Cell {

    val state: MutableLiveData<State> = MutableLiveData(State.Empty)
    var job: Job? = null


    companion object {
        var factor = 1F;
        private const val speed = 500
    }

    fun plant() {
        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Main) {
            state.value!!.next(this@Cell)
        }
    }

    fun harvest(): Int =
        when (state.value) {
            State.Empty -> throw RuntimeException("harvesting empty cell")
            State.Planted, State.Green -> 0
            null -> throw RuntimeException("state is null")
            else -> {
                job?.cancel()
                val res = state.value!!.income
                state.value = State.Empty
                res
            }
        }


    enum class State(val color: Int, val income: Int = 0) {
        Empty(Color.WHITE) {
            override suspend fun next(cell: Cell) {
                cell.state.value = Planted
                cell.state.value!!.next(cell)
            }
        },
        Planted(Color.BLACK) {
            override suspend fun next(cell: Cell) {
                delay((500 / factor).toLong())
                cell.state.value = Green
                cell.state.value!!.next(cell)
            }
        },
        Green(Color.GREEN) {
            override suspend fun next(cell: Cell) {
                delay((700 / factor).toLong())
                cell.state.value = Immature
                cell.state.value!!.next(cell)
            }
        },
        Immature(Color.YELLOW, 3) {
            override suspend fun next(cell: Cell) {
                delay((900 / factor).toLong())
                cell.state.value = Mature
                cell.state.value!!.next(cell)
            }
        },
        Mature(Color.RED, 5) {
            override suspend fun next(cell: Cell) {
                delay((1000 / factor).toLong())
                cell.state.value = Overgrow
            }
        },
        Overgrow(Color.GRAY, -1) {
            override suspend fun next(cell: Cell) {
            }
        };

        abstract suspend fun next(cell: Cell);
    }
}