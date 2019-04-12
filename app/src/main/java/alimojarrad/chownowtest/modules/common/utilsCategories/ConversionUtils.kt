package alimojarrad.chownowtest.modules.common.utilsCategories

import alimojarrad.chownowtest.MyApplication
import alimojarrad.chownowtest.R
import alimojarrad.chownowtest.models.Address
import android.content.Context
import android.widget.TextView


object ConversionUtils {

    fun showAddress(textview: TextView?, address: Address?, street2OnNewLine: Boolean? = false): String {
        return address?.let {
            address.formatted_address?.let {
                it
            } ?: run {
                address.street_address1 +
                        (if (address.street_address2 != null && (address.street_address2 ?: "").isNotEmpty()) {
                            "${if (street2OnNewLine == true) "\n" else ", "}${address.street_address2}"
                        } else "") +
                        (if (address.city != null && (address.city ?: "").isNotEmpty()) {
                            "\n${address.city}"
                        } else "") +
                        (if (address.state != null && (address.state ?: "").isNotEmpty()) {
                            if (address.city != null) {
                                ", ${address.state}"
                            } else {
                                "\n${address.state}"
                            }
                        } else "") +
                        (if (address.zip != null && (address.zip ?: "").isNotEmpty()) {
                            if (address.state != null) {
                                " ${address.zip}"
                            } else {
                                address.zip
                            }
                        } else "")
            }
        } ?: run {
            textview?.setTextColor(MyApplication.appContext.resources.getColor(R.color.disabled, null))
            "No address available"
        }
    }

    fun getDpFromInt(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        val pixels = (dpValue * scale + 0.5f)
        return pixels.toInt()
    }

    fun getFormattedPhoneNumber(phone: String?, textview: TextView? = null): String {
        phone?.let {
            var new = Regex("[^0-9]").replace(phone, "")
            return new.substring(0, 3) + "-" + new.substring(3, 6) + "-" + new.substring(6, new.length)
        } ?: run {
            textview?.setTextColor(MyApplication.appContext.resources.getColor(R.color.disabled, null))
            return "No phone number available"
        }
    }
}