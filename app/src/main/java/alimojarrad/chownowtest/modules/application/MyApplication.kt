package alimojarrad.chownowtest

import alimojarrad.chownowtest.services.api.API
import alimojarrad.chownowtest.services.api.ApiManager
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import timber.log.Timber



class MyApplication : Application() {
    companion object {
        private const val TAG = "MyApplication"
        lateinit var appContext: Context
            private set
    }


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Timber.plant(Timber.DebugTree())
        }
        API.initApi(ApiManager(appContext.resources.getString(R.string.base_url)))
    }

}