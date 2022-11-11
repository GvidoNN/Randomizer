package my.lovely.randomizer2.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import my.lovely.randomizer2.R

class FragmentCoin : Fragment(R.layout.fragment_coin) {

    lateinit var iv_coin: ImageView
    lateinit var tv_tap: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCoinTap()
    }

    private fun onCoinTap() {
        iv_coin = requireView().findViewById(R.id.iv_coin)
        iv_coin.setOnClickListener {
            val randomNumber = (1..2).random()
            if (randomNumber == 1) {
                flipTheCoin(R.drawable.ic_reshka)
                editText(R.string.tail)
            } else {
                flipTheCoin(R.drawable.ic_orel)
                editText(R.string.head)
            }
        }
    }

    private fun flipTheCoin(imageId: Int) {
        iv_coin.animate().apply {
            duration = 1000
            rotationYBy(1800f)
            iv_coin.isClickable = false
        }.withEndAction {
            iv_coin.setImageResource(imageId)
            iv_coin.isClickable = true
        }.start()
    }

    private fun editText(stringId: Int) {
        tv_tap = requireView().findViewById(R.id.tvTap)
        tv_tap.visibility = View.INVISIBLE
        tv_tap.text = getString(stringId)
        android.os.Handler().postDelayed({ tv_tap.visibility = View.VISIBLE }, 1000)
    }
}