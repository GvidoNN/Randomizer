package my.lovely.randomizer2.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.slider.Slider
import my.lovely.randomizer2.R

class FragmentTeams : Fragment(R.layout.fragment_teams) {

    lateinit var edNames: EditText
    lateinit var countTeams: Slider
    lateinit var btTeamGenerate: Button
    lateinit var tvTeam1: TextView
    lateinit var tvTeam2: TextView
    lateinit var tvTeam3: TextView
    lateinit var tvTeam4: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findView()
        sliderListener()
        onClickButtonGenerateTeam()
    }

    private fun findView() {
        edNames = requireView().findViewById(R.id.edNames)
        countTeams = requireView().findViewById(R.id.countTeams)
        btTeamGenerate = requireView().findViewById(R.id.btTeamGenerate)
        tvTeam1 = requireView().findViewById(R.id.tvTeam1)
        tvTeam2 = requireView().findViewById(R.id.tvTeam2)
        tvTeam3 = requireView().findViewById(R.id.tvTeam3)
        tvTeam4 = requireView().findViewById(R.id.tvTeam4)
    }

    private fun sliderListener() {
        countTeams.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
            }
        })

    }

    private fun onClickButtonGenerateTeam() {
        btTeamGenerate.setOnClickListener {
            readSplitText(edNames.text.toString(), countTeams.value.toInt()).toString()
        }
    }

    private fun listToString(team: List<String>): String {
        var column = ""
        team.forEach { column += "$it \n" }
        return column
    }

    private fun columnTeamGone() {
        tvTeam1.visibility = View.GONE
        tvTeam2.visibility = View.GONE
        tvTeam3.visibility = View.GONE
        tvTeam4.visibility = View.GONE
    }

    private fun readSplitText(text: String, count: Int): List<String> {
        val textList = text.split(" ").shuffled()

        val teamTextViews = listOf(tvTeam1, tvTeam2, tvTeam3, tvTeam4)

        columnTeamGone()

        for (i in 0 until count) {
            teamTextViews[i].visibility = View.VISIBLE
            val startIndex = (i * textList.size) / count
            val endIndex = ((i + 1) * textList.size) / count
            teamTextViews[i].text = listToString(textList.subList(startIndex, endIndex))
        }

        return textList
    }

}