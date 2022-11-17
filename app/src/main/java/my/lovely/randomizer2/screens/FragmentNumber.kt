package my.lovely.randomizer2.screens

import android.icu.number.NumberRangeFormatter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.slider.Slider
import my.lovely.randomizer2.R
import kotlin.collections.ArrayList

class FragmentNumber : Fragment(R.layout.fragment_number) {

    lateinit var countValuesSlider: Slider
    lateinit var btResult: Button
    lateinit var edMinValue: EditText
    lateinit var edMaxValue: EditText
    lateinit var tvConclusion: TextView

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
    }

    private fun sliderListener() {
        countValuesSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                Log.d("MyLog", "Start Tracking Touch")
            }

            override fun onStopTrackingTouch(slider: Slider) {
                Log.d("MyLog", countValuesSlider.value.toInt().toString())
            }
        })
    }

    private fun buttonClick() {
        btResult.setOnClickListener {
            try{
                Log.d(
                    "MyLog",
                    randomNumbers(
                        edMinValue.text.toString().toInt(),
                        edMaxValue.text.toString().toInt(),
                        countValuesSlider.value.toInt()
                    ).toString()
                )
                editText(
                    randomNumbers(
                        edMinValue.text.toString().toInt(),
                        edMaxValue.text.toString().toInt(),
                        countValuesSlider.value.toInt()
                    )
                )

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

    private fun editText(numbers: ArrayList<Int>) {
        var text = ""
        numbers.forEach { text += "  $it" }
        tvConclusion.text = text
    }

}