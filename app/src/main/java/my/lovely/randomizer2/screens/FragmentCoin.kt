package my.lovely.randomizer2.screens

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.lovely.randomizer2.AppMainState
import my.lovely.randomizer2.R

class FragmentCoin : Fragment(R.layout.fragment_coin) {

    lateinit var iv_coin: ImageView
    lateinit var tv_tap: TextView
    private var interAd: InterstitialAd? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCoinTap()
        loadInterAd()
    }

    private fun onCoinTap() {
        iv_coin = requireView().findViewById(R.id.iv_coin)
        iv_coin.setOnClickListener {
            showInterAd()
            lifecycleScope.launch{
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

    private suspend fun editText(stringId: Int) {
        tv_tap = requireView().findViewById(R.id.tvTap)
        tv_tap.visibility = View.INVISIBLE
        delay(1000)
        tv_tap.text = getString(stringId)
        tv_tap.visibility = View.VISIBLE
    }

    private fun loadInterAd(){
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(), "ca-app-pub-3940256099942544/1033173712",
            adRequest, object:  InterstitialAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interAd = ad
                }
            })
    }

    private fun showInterAd(){
        if(interAd != null){
            interAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdDismissedFullScreenContent() {
                    interAd = null
                    loadInterAd()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    interAd = null
                    loadInterAd()
                }

                override fun onAdShowedFullScreenContent() {
                    interAd = null
                    loadInterAd()
                }

            }
            interAd?.show(requireContext() as Activity)
        }
    }
}