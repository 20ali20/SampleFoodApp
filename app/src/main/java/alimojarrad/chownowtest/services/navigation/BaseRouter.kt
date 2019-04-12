package alimojarrad.chownowtest
import alimojarrad.chownowtest.services.navigation.BaseFragment
import java.io.Serializable


interface BaseRouter : Serializable {
    fun display(fragment: BaseFragment, start: Int = R.anim.enter_from_right, end: Int = R.anim.exit_to_left, skipLoading: Boolean? = false)
    fun dismiss(start: Int = R.anim.enter_from_left, end: Int = R.anim.exit_to_right)
    fun setFragmentReady()
    fun setFragmentFailed()


}

