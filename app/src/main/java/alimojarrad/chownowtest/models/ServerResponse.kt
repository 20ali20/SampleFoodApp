package alimojarrad.chownowtest.models

import androidx.annotation.IntDef

/**
 * Created by amojarrad on 3/21/18.
 */
data class ServerResponse(
        var isSuccessful: Boolean? = false,
        var reason: String? = "",
        var type: Int? = SR_GETRESTAURANT

)

@Retention(AnnotationRetention.SOURCE)
@IntDef(
        SR_GETRESTAURANT
)
annotation class ServerResponseType

const val SR_GETRESTAURANT = 0



data class ErrorDom(
        var message: String? = null
)

