package alimojarrad.chownowtest

import alimojarrad.chownowtest.modules.application.MainActivity
import alimojarrad.chownowtest.services.navigation.BaseActivity
import android.os.Bundle


/**
 * Created by amojarrad on 1/29/18.
 */
class SplashActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.startActivity(this)
    }


}