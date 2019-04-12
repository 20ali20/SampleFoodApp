package alimojarrad.chownowtest.services.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber




abstract class BaseActivity : AppCompatActivity() {

    open fun showFragment() {}
    open fun hideFragment() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        Timber.e("Low Memory")
        System.gc()
    }
}