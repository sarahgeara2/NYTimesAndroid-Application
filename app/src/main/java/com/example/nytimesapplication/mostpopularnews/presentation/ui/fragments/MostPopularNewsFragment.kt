package com.example.nytimesapplication.mostpopularnews.presentation.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nytimesapplication.R
import com.example.nytimesapplication.common.GlobalFunctions
import com.example.nytimesapplication.common.models.NewsResponse
import com.example.nytimesapplication.core.BaseFragment
import com.example.nytimesapplication.core.di.ViewModelInjectionField
import com.example.nytimesapplication.core.di.qualifiers.ViewModelInjection
import com.example.nytimesapplication.core.vo.Status
import com.example.nytimesapplication.mostpopularnews.presentation.ui.adapters.NewsAdapter
import com.example.nytimesapplication.mostpopularnews.presentation.viewmodel.MostPopularNewsViewModel
import kotlinx.android.synthetic.main.fragment_most_popular_news.view.*
import java.util.ArrayList
import javax.inject.Inject


class MostPopularNewsFragment : BaseFragment() {

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<MostPopularNewsViewModel>
    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_most_popular_news, container, false)
        mActivity = activity!!
        getNews()
        root.srlNews.setOnRefreshListener {
            getNews()
        }
        return root
    }

    private fun getNews() {
        viewModel.get().getMostPopularNews()
        viewModel.get().getMostPopularNewsViewModel.removeObservers(viewLifecycleOwner)
        viewModel.get().getMostPopularNewsViewModel.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { repos ->
                repos?.let {
                    when (repos.status) {
                        Status.LOADING -> {
                            showHideLoading(true)
                        }
                        Status.ERROR -> {
                            showHideLoading(false)
                            GlobalFunctions.showToast(mActivity,"Something went wrong, please try again later")
                        }
                        Status.SUCCESS -> {
                            showHideLoading(false)
                            var news = repos.data!!.results as ArrayList<NewsResponse?>
                            var landingPagerAdapter  = NewsAdapter(mActivity,news)
                            root.rvNews.adapter = landingPagerAdapter
                        }
                    }
                }
            }
        )
    }

    fun showHideLoading(show:Boolean){
        if(show){
            root.srlNews.isRefreshing = true
        }else{
            root.srlNews.isRefreshing = false
        }
    }

}
