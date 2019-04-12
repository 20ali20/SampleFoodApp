package alimojarrad.chownowtest.services.api.interfaces

import alimojarrad.chownowtest.models.Restaurant
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by amojarrad on 2/2/18.
 */
interface RestaurantInterface {

    @GET("api/company/1")
    fun getRestaurant(): Single<Response<Restaurant>>

}