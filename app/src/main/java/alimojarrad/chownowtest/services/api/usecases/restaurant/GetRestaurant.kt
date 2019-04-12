package alimojarrad.chownowtest.services.api.usecases.restaurant

import alimojarrad.chownowtest.models.Restaurant
import alimojarrad.chownowtest.services.api.API
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by amojarrad on 2/2/18.
 */
object GetRestaurant  {
     fun execute(): Single<Response<Restaurant>> {
        return API.restaurant.getRestaurant()
    }
}
