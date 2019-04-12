package alimojarrad.chownowtest.modules.restaurant


import alimojarrad.chownowtest.R
import alimojarrad.chownowtest.models.Location
import alimojarrad.chownowtest.models.Restaurant
import alimojarrad.chownowtest.models.ServerResponse
import alimojarrad.chownowtest.modules.restaurant.detail.RestaurantLocationDetail
import alimojarrad.chownowtest.services.navigation.BaseFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sidecarhealth.modules.common.AdapterClickListener
import com.sidecarhealth.modules.common.PopupMessage
import com.sidecarhealth.modules.common.adapters.nonPaginated.NonPaginatedAdapterRecylerview
import com.sidecarhealth.modules.common.customItemDecoration.CustomDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_restaurant.*
import timber.log.Timber


class WrapContentLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Timber.e("CAUGHT $e")
        } catch (e: IllegalStateException) {
            Timber.e("CAUGHT $e")
        } catch (e: IllegalArgumentException) {
            Timber.e("CAUGHT $e")
        }
    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}

class RestaurantFragment : BaseFragment() {

    companion object {
        fun newInstance(): RestaurantFragment {
            return RestaurantFragment()
        }
    }

    private lateinit var viewModel: RestaurantViewModel
    private lateinit var adapter: NonPaginatedAdapterRecylerview<Location>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupInteractions()
        setupRestaurantList()
        setupViewModel()
        viewModel.getRestaurant()
        restaurant_swiprefresh.isRefreshing = true
        this@RestaurantFragment.router?.setFragmentReady()
    }


    private fun setupViews() {

    }

    private fun setupRestaurantList() {
        val fragTag = "dialog"
        val itemListener = (object : AdapterClickListener {
            override fun onClick(v: View, position: Int) {
                try {
                    val location = adapter.getItem(position)
                    location?.let {
                        val ft = childFragmentManager.beginTransaction()
                        val prev = childFragmentManager.findFragmentByTag(fragTag)
                        if (prev != null) {
                            ft.remove(prev)
                        }
                        ft.addToBackStack(null)
                        RestaurantLocationDetail.newInstance(it).show(ft, fragTag)
                    }

                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        })
        restaurant_recyclerview.layoutManager = WrapContentLinearLayoutManager(context!!)
        val dividerItemDecoration = CustomDividerItemDecoration(resources.getDrawable(R.drawable.s_divider, null))
        restaurant_recyclerview.addItemDecoration(dividerItemDecoration)
        adapter = NonPaginatedAdapterRecylerview(
            context!!,
            R.layout.item_restaurant_list,
            RestaurantListViewHolder::class.java,
            itemListener
        )
        restaurant_recyclerview.adapter = adapter
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)

        val restaurantObserver = Observer<Restaurant> {
            updateView(it)
        }

        val serverResponseObserver = Observer<ServerResponse> {
            restaurant_swiprefresh.isRefreshing = false
            it?.let {
                if (it.isSuccessful!!) {

                } else {
                    PopupMessage.createPopupMessage(context!!, it.reason ?: "")
                }
            }
        }
        viewModel.restaurant.observe(this, restaurantObserver)
        viewModel.serverResponse.observe(this, serverResponseObserver)
    }

    private fun setupInteractions() {
        restaurant_swiprefresh.setOnRefreshListener {
            viewModel.getRestaurant()
        }
    }

    private fun updateView(restaurant: Restaurant) {
        restaurant_title.text = restaurant.name ?: "N/A"
        (restaurant.locations ?: ArrayList()).let {
            adapter.updateList(it)
        }
    }

}