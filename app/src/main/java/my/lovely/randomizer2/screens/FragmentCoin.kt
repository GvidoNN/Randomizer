package my.lovely.randomizer2.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import my.lovely.randomizer2.R

class FragmentCoin : Fragment(R.layout.fragment_coin) {

    lateinit var iv_coin : ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onCoinTap()
    }

    private fun onCoinTap(){
        iv_coin = requireView().findViewById(R.id.iv_coin)
        iv_coin.setOnClickListener{
            val randomNumber = (1..2).random()
            if(randomNumber == 1) flipTheCoin(R.drawable.ic_reshka, "heads")
            else flipTheCoin(R.drawable.ic_orel, "tails")
        }
    }

    private fun flipTheCoin(imageId: Int, coinSide: String){
        iv_coin.animate().apply{
            duration = 1000
            rotationYBy(1800f)
            iv_coin.isClickable = false
        }.withEndAction{
            iv_coin.setImageResource(imageId)
            iv_coin.isClickable = true
        }.start()
    }

}