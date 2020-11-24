package com.example.nytimesapplication.newsDetails.presentation.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.nytimesapplication.R
import com.example.nytimesapplication.common.GlobalVars
import com.example.nytimesapplication.common.models.NewsResponse
import com.example.nytimesapplication.core.BaseFragment
import com.example.nytimesapplication.core.di.ViewModelInjectionField
import com.example.nytimesapplication.core.di.qualifiers.ViewModelInjection
import com.example.nytimesapplication.newsDetails.presentation.viewmodel.NewsDetailsViewModel
import kotlinx.android.synthetic.main.fragment_news_details.view.*
import javax.inject.Inject


class NewsDetailsFragment : BaseFragment() {

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<NewsDetailsViewModel>
    lateinit var root: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_news_details, container, false)
        mActivity = requireActivity()
        var news = mActivity.intent.getSerializableExtra(GlobalVars.NEWS_BEAN) as NewsResponse

        news.let {
            news.title?.let {
                root.tvNews.text = it
            }
            news.byline?.let {
                root.tvAuthor.text = it
            }
            news.publishedDate?.let {
                root.tvDate.text = it
            }
            news.abstract?.let {
                root.tvDescription.text = it
            }
            news!!.media!!.get(0)!!.medias!!.get(2)!!.url.let {
                Glide.with(root.ivNews.context).load(it)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(root.ivNews)
            }
        }

        return root
    }


}
