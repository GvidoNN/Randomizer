package my.lovely.randomizer2.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.slider.Slider
import my.lovely.randomizer2.R
import kotlin.collections.ArrayList

class FragmentNumber : Fragment(R.layout.fragment_number) {

    private lateinit var countValuesSlider: Slider
    private lateinit var btResult: Button
    private lateinit var edMinValue: EditText
    private lateinit var edMaxValue: EditText
    private lateinit var tvConclusion: TextView
    private lateinit var checkBox: CheckBox

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findView()
        buttonClick()
        sliderListener()
    }

    private fun findView() {
        countValuesSlider = requireView().findViewById(R.id.countValueSlider)
        btResult = requireView().findViewById(R.id.btResult)
        edMaxValue = requireView().findViewById(R.id.edMaxValue)
        edMinValue = requireView().findViewById(R.id.edMinValue)
        tvConclusion = requireView().findViewById(R.id.tvConclusion)
        checkBox = requireView().findViewById(R.id.checkBoxNumber)
    }

    private fun sliderListener() {
        countValuesSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
            }
        })
    }

    private fun buttonClick() {
        btResult.setOnClickListener {
            try{
                if(checkBox.isChecked){
                    editText(
                        randomNumbersWithoutDublicates(
                            edMinValue.text.toString().toInt(),
                            edMaxValue.text.toString().toInt(),
                            countValuesSlider.value.toInt()
                        )
                    )
                } else {
                    editText(
                        randomNumbers(
                            edMinValue.text.toString().toInt(),
                            edMaxValue.text.toString().toInt(),
                            countValuesSlider.value.toInt()
                        )
                    )
                }

            } catch (e: java.util.NoSuchElementException){
                edMaxValue.error = getString(R.string.value_error)
                edMinValue.error = getString(R.string.value_error)
            } catch (e: java.lang.NumberFormatException ){
                edMaxValue.error = getString(R.string.no_value_error)
                edMinValue.error = getString(R.string.no_value_error)
            }
        }
    }

    private fun randomNumbers(min: Int, max: Int, count: Int): ArrayList<Int> {
        var randomArray = arrayListOf<Int>()
        for (i in 1..count) {
            randomArray.add((min..max).random())
        }
        return randomArray
    }

    private fun randomNumbersWithoutDublicates(min: Int, max: Int, count: Int): ArrayList<Int> {
        if (count > max - min + 1) {
            throw IllegalArgumentException("Count should be less than or equal to the range of numbers")
        }

        val numbers = ArrayList<Int>()
        for (i in min..max) {
            numbers.add(i)
        }

        val randomArray = ArrayList<Int>()

        for (i in 0 until count) {
            val randomIndex = (min until max - i).random()
            randomArray.add(numbers[randomIndex])
            // Swap the selected element with the last element to avoid duplicates
            numbers[randomIndex] = numbers[max - i - 1]
        }

        return randomArray
    }

    private fun editText(numbers: ArrayList<Int>) {
        var text = ""
        numbers.forEach { text += "  $it" }
        tvConclusion.text = text
    }

}