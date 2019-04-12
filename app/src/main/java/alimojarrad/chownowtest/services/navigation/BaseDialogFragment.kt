package alimojarrad.chownowtest.services.navigation


import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.io.Serializable

open class BaseDialogFragment : DialogFragment(), Serializable {



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.setCanceledOnTouchOutside(true)
    }
}



