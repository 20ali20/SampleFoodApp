package alimojarrad.chownowtest.modules.common.utilsCategories

import android.content.Context
import android.content.Intent
import android.net.Uri


object ApplicationUtils {

    fun callNavigation(context: Context, lat: String, lon: String) {
        val gmmIntentUri = Uri.parse("google.navigation:q=$lat,$lon")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.`package` = "com.google.android.apps.maps"
        context.startActivity(mapIntent)
    }

    fun callGoogleMaps(context: Context, lat: String, lon: String) {
        val gmmIntentUri = Uri.parse("geo:$lat,$lon")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.`package` = "com.google.android.apps.maps"
        context.startActivity(mapIntent)
    }

    fun callIntent(context: Context, number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        context.startActivity(intent)
    }
}