package my.lovely.randomizer2.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import my.lovely.randomizer2.R

class FragmentDice : Fragment(R.layout.fragment_dice) {

    lateinit var imDice1: ImageView
    lateinit var imDice2: ImageView
    lateinit var imDice3: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onDiceTap()
    }

    private fun findView() {
        imDice1 = requireView().findViewById(R.id.imDice1)
        imDice2 = requireView().findViewById(R.id.imDice2)
        imDice3 = requireView().findViewById(R.id.imDice3)
    }

    private fun onDiceTap() {
        findView()
        imDice1.setOnClickListener {
            imDice1.setImageResource(R.drawable.ic_cube)
            rollDice(randomNumber(), imDice1)
        }
        imDice2.setOnClickListener {
            imDice2.setImageResource(R.drawable.ic_cube)
            rollDice(randomNumber(), imDice2)
        }
        imDice3.setOnClickListener {
            imDice3.setImageResource(R.drawable.ic_cube)
            rollDice(randomNumber(), imDice3)
        }
    }

    private fun rollDice(imageId: Int, imDice: ImageView) {
        imDice.animate().apply {
            duration = 1000
            rotationYBy(1800f)
            rotationXBy(1800f)
            imDice.isClickable = false
        }.withEndAction {
            imDice.setImageResource(imageId)
            imDice.isClickable = true
        }.start()
    }

    private fun randomNumber(): Int {
        val number = (1..6).random()
        return when (number) {
            1 -> R.drawable.ic_dice1
            2 -> R.drawable.ic_dice2
            3 -> R.drawable.ic_dice3
            4 -> R.drawable.ic_dice4
            5 -> R.drawable.ic_dice5
            6 -> R.drawable.ic_dice6
            else -> R.drawable.ic_cube
        }
    }
}