package alimojarrad.chownowtest.modules.restaurant



import alimojarrad.chownowtest.models.Restaurant
import alimojarrad.chownowtest.models.SR_GETRESTAURANT
import alimojarrad.chownowtest.models.ServerResponse
import alimojarrad.chownowtest.services.api.usecases.restaurant.GetRestaurant
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sidecarhealth.modules.common.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


/**
 * Created by amojarrad on 2/20/18.
 */
open class RestaurantViewModel : ViewModel() {

    var restaurant = MutableLiveData<Restaurant>()


    var serverResponse = MutableLiveData<ServerResponse>()
    private var disposable = CompositeDisposable()


    fun getRestaurant() {
        disposable.add(
                GetRestaurant.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        it.body()?.let {
                                            this.restaurant.value = it
                                            serverResponse.value = ServerResponse(true, "", SR_GETRESTAURANT)
                                        }
                                    } else {
                                        serverResponse.value = ServerResponse(false, "We could not process your request because ${Utils.network.getErrorMessage(it)}", SR_GETRESTAURANT)
                                    }
                                },
                                {
                                    Timber.e(it)
                                    serverResponse.value = ServerResponse(false, "We could not update the member.", SR_GETRESTAURANT)
                                }
                        )
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}


