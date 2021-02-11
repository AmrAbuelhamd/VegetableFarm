package com.blogspot.soyamr.vegetablefarm

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CellViewModel : ViewModel() {

    private val cells: MutableList<Cell> = ArrayList()

    private val _cellState: MutableLiveData<MutableList<MutableLiveData<Cell.State>>> =
        MutableLiveData(ArrayList(16))
    val cellState: LiveData<MutableList<MutableLiveData<Cell.State>>> = _cellState

    init {
        for (i in 0..15) {
            val cell = Cell()
            cells.add(cell)
            _cellState.value?.add(cell.state)
        }
    }

    fun plant(view: View) {
        cells[view.tag.toString().toInt()].plant()
    }
}