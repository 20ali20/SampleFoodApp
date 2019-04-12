package alimojarrad.chownowtest.services.api

import alimojarrad.chownowtest.services.api.interfaces.RestaurantInterface

/**
 * Created by amojarrad on 1/29/18.
 */
object API {

    lateinit var restaurant : RestaurantInterface

//    lateinit var care : CareInterface


    fun initApi(apiManager: ApiManager){
        restaurant = apiManager.createAPI(RestaurantInterface::class.java)

    }


}