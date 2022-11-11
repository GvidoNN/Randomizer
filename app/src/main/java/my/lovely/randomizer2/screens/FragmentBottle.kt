package my.lovely.randomizer2.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import my.lovely.randomizer2.R

class FragmentBottle : Fragment(R.layout.fragment_bottle) {

    lateinit var imBottle: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findView()
        onBottleTap()
    }

    private fun findView() {
        imBottle = requireView().findViewById(R.id.imBottle)
    }

    private fun onBottleTap() {
        imBottle.setOnClickListener {
            rollBottle()
        }
    }

    private fun rollBottle() {
        imBottle.animate().apply {
            duration = (3000..5000).random().toLong()
            rotation(randomPosition())
            imBottle.isClickable = false
        }.withEndAction {
            imBottle.isClickable = true
        }.start()
    }

    private fun randomPosition(): Float {
        val number: Float = (0..3600).random().toFloat()
        return number
    }


}