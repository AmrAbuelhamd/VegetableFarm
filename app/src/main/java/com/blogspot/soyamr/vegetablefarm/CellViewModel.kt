package com.blogspot.soyamr.vegetablefarm

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CellViewModel : ViewModel() {

    private val cells: MutableList<Cell> = ArrayList()

    private val _cellState: MutableLiveData<MutableList<MutableLiveData<Cell.State>>> =
        MutableLiveData(ArrayList(16))
    val cellState: LiveData<MutableList<MutableLiveData<Cell.State>>> = _cellState

    private val _money: MutableLiveData<Int> = MutableLiveData(100)
    val money: LiveData<Int> = _money

    private val _day: MutableLiveData<Int> = MutableLiveData(0)
    val day: LiveData<Int> = _day


    init {
        for (i in 0..15) {
            val cell = Cell()
            cells.add(cell)
            _cellState.value?.add(cell.state)
        }

        viewModelScope.launch {
            while (true) {
                delay((100/Cell.factor).toLong())
                _day.value = _day.value?.plus(1)
            }
        }
    }

    fun plant(view: View) {
        val index = view.tag.toString().toInt();
        if (cellState.value?.get(index)?.value == Cell.State.Empty) {
            if (_money.value!! >= 2) {
                cells[index].plant()
                _money.value = _money.value?.minus(2)
            }
        } else
            _money.value = _money.value?.plus(cells[index].harvest())

    }

    fun onValueChanged(value: Float) {
        if (value == 0F)
            return
        Cell.factor = value
    }
}