package my.lovely.randomizer2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as AppMainState).showAdIfAvailable(this){
            Toast.makeText(this,"Start App", Toast.LENGTH_LONG).show()
        }
    }
}