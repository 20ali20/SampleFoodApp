package alimojarrad.chownowtest.modules.application

import alimojarrad.chownowtest.BaseRouter
import alimojarrad.chownowtest.R
import alimojarrad.chownowtest.modules.restaurant.RestaurantFragment
import alimojarrad.chownowtest.services.navigation.BaseActivity
import alimojarrad.chownowtest.services.navigation.BaseFragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), BaseRouter {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            (context as? BaseActivity)?.finish()
        }
    }

    private var fragmentStack: Stack<BaseFragment> = Stack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        display(RestaurantFragment.newInstance())

    }


    override fun display(fragment: BaseFragment, start: Int, end: Int, skipLoading: Boolean?) {
        fragment.loadingText?.let {
            progress_text.text = it
            progress_text.visibility = View.VISIBLE
        } ?: run {
            progress_text.text = ""
            progress_text.visibility = View.GONE
        }

        if (skipLoading!!) {
            //Do nothing
        } else {
            hideFragment()
        }
        val manager = supportFragmentManager
        fragmentStack.push(fragment)
        fragment.router = this
        manager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.fragmentframe, fragment)
            .hide(fragment)
            .disallowAddToBackStack()
            .commit()
    }

    override fun dismiss(start: Int, end: Int) {
        hideFragment()

        val manager = supportFragmentManager
        if (fragmentStack.size <= 1) {
            return
        }
        fragmentStack.pop()
        manager.beginTransaction()
//                .setCustomAnimations(start, end)
            .replace(R.id.fragmentframe, fragmentStack.peek())
            .hide(fragmentStack.peek())
            .disallowAddToBackStack()
            .commit()
    }

    override fun showFragment() {
        fragmentframe.visibility = View.VISIBLE
        progress_container.visibility = View.GONE
        if (fragmentStack.isNotEmpty()) {
            val manager = supportFragmentManager

            manager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .show(fragmentStack.peek())
                .commit()
        }
    }

    override fun hideFragment() {
        fragmentframe.visibility = View.GONE
        progress_container.visibility = View.VISIBLE
    }

    override fun setFragmentReady() {
        showFragment()
        progress_text.text = ""
        progress_text.visibility = View.GONE
    }

    override fun setFragmentFailed() {
        fragmentframe.visibility = View.GONE
        progress_container.visibility = View.GONE
    }
}
