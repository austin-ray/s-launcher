package austinray.io.slauncher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val launchIntent = packageManager.getLaunchIntentForPackage("com.android.chrome")
            startActivity(launchIntent)
        }
    }
}
