package kryx07.expensereconcilerclient.base.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.HideRefresher
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber

abstract class RefreshableFragment() : Fragment() {

    lateinit var swipeRefresher: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        setupSwipeRefresher(container as SwipeRefreshLayout)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    fun setupSwipeRefresher(swipeRefresher: SwipeRefreshLayout) {

        this.swipeRefresher = swipeRefresher

        swipeRefresher.setOnRefreshListener({
            swipeRefresher.setColorSchemeResources(
                    R.color.colorPrimary,
                    R.color.accent_material_light,
                    R.color.dark_yellow,
                    R.color.colorAccent)

            onRefresh()
            Timber.e("On refresh listener")
        })

    }

    @Subscribe
    fun hideRefresher(hideRefresher: HideRefresher) {
        this.swipeRefresher.isRefreshing = false
    }

    abstract fun onRefresh()


}