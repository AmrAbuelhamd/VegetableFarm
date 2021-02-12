package com.blogspot.soyamr.vegetablefarm

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.slider.Slider

class CellViewModel : ViewModel() {

    private val cells: MutableList<Cell> = ArrayList()

    private val _cellState: MutableLiveData<MutableList<MutableLiveData<Cell.State>>> =
        MutableLiveData(ArrayList(16))
    val cellState: LiveData<MutableList<MutableLiveData<Cell.State>>> = _cellState

    private val _money: MutableLiveData<Int> = MutableLiveData(100)
    val money: LiveData<Int> = _money


    init {
        for (i in 0..15) {
            val cell = Cell()
            cells.add(cell)
            _cellState.value?.add(cell.state)
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
        Cell.factor = value
    }
}