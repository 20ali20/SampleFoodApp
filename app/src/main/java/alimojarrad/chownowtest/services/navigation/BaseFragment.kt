package alimojarrad.chownowtest.services.navigation

import alimojarrad.chownowtest.BaseRouter
import android.content.Context
import androidx.fragment.app.Fragment
import java.io.Serializable


open class BaseFragment : Fragment(), Serializable {


    open var router: BaseRouter? = null
    var loadingText : String?=null



    fun onBackPressed() {
        router?.dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseRouter) {
            this.router = context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        router = null
    }
}
