package io.vinicius.androidcommon.view.home

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.vinicius.androidcommon.R
import io.vinicius.androidcommon.constant.MenuOptions
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.view.BaseFragment
import io.vinicius.androidcommon.view.country.CountryFragment
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment()
{
    companion object {
        fun newInstance() = HomeFragment()
    }

    private val adapter = HomeAdapter(arrayOf(MenuOptions.COUNTRY, MenuOptions.FIREBASE_FIRESTORE))

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater!!.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        // Setting up the RecyclerView
        rvHome.setHasFixedSize(true)
        rvHome.addItemDecoration(DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL))
        rvHome.layoutManager = LinearLayoutManager(this.activity)
        rvHome.adapter = adapter
    }

    override fun bindViewModel()
    {
        disposables.add(
                adapter.itemClick
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        when(it) {
                            MenuOptions.COUNTRY -> FragmentUtil.push(activity, CountryFragment.newInstance())
                            else -> {}
                        }
                    }
        )
    }
}
