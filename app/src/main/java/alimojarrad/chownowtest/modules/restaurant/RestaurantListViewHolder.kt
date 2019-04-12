package alimojarrad.chownowtest.modules.restaurant

import alimojarrad.chownowtest.R
import alimojarrad.chownowtest.models.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sidecarhealth.modules.common.AdapterClickListener
import com.sidecarhealth.modules.common.Utils
import com.sidecarhealth.modules.common.views.BaseViewHolder
import kotlinx.android.synthetic.main.item_restaurant_list.view.*

class RestaurantListViewHolder(itemView: View, val itemListeners: AdapterClickListener) : BaseViewHolder<Location>(itemView, itemListeners) {

    override fun getView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant_list, parent, false)
    }


    override fun bind(item: Location, position: Int) {
        itemView.irl_name.text = item.short_name?:"N/A"
        itemView.irl_phone.text = Utils.conversion.getFormattedPhoneNumber(item.phone, itemView.irl_phone)
        itemView.irl_address.text = Utils.conversion.showAddress(itemView.irl_address,item.address)

        itemView.setOnClickListener {
            itemListeners.let {
                it.onClick(itemView, position)
            }
        }


    }
}
