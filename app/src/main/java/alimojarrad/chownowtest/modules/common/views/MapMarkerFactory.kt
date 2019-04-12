package alimojarrad.chownowtest.modules.common.views

import alimojarrad.chownowtest.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import timber.log.Timber


class MapMarkerFactory {
    var currentBitmap: Bitmap? = null



    fun getRoundMarker(context: Context,background: Int?= R.drawable.v_marker): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(
                context.resources, background!!, null)
        if (vectorDrawable == null) {
            Timber.e( "Requested vector resource was not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        recycle()
        currentBitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(currentBitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(currentBitmap)
    }

    fun recycle(){
        currentBitmap?.let {
            it.recycle()
        }
    }
}

