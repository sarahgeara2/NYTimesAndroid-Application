package com.example.nytimesapplication.mostpopularnews.presentation.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nytimesapplication.R
import com.example.nytimesapplication.common.GlobalFunctions
import com.example.nytimesapplication.common.models.NewsResponse
import java.util.*

class NewsAdapter(
        private var activity: Activity,
        private var menuItems: ArrayList<NewsResponse?>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    override fun getItemCount(): Int {
        return if (menuItems.isNullOrEmpty()) 0 else menuItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            menuItems[position] == null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    fun getItemAtPosition(position: Int): NewsResponse? {
        return menuItems[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemListHolder(
                inflater,
                parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemListHolder) {
            val item: NewsResponse = menuItems[position]!!
            holder.bind(item)
            holder.rlNewsWrapper.setTag(position)
            holder.rlNewsWrapper.setOnClickListener {
                var posi = it.getTag() as Int
                GlobalFunctions.goToNewsDetailsActivity(activity,menuItems[posi]!!)
            }
        }
    }

    class ItemListHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(
                    inflater.inflate(
                            R.layout.item_news,
                            parent,
                            false
                    )
            ) {
         var tvNews: TextView = itemView.findViewById(R.id.tvNews)
         var tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
         var tvDate: TextView = itemView.findViewById(R.id.tvDate)
         var ivNews: ImageView = itemView.findViewById(R.id.ivNews)
         var rlNewsWrapper: RelativeLayout = itemView.findViewById(R.id.rlNewsWrapper)

        fun bind(item: NewsResponse) {
            item.title?.let {
                tvNews.text = it
            }
            item.byline?.let {
                tvAuthor.text = it
            }
            item.publishedDate?.let {
                tvDate.text = it
            }
            item!!.media!!.get(0)!!.medias!!.get(1)!!.url.let {
                Glide.with(ivNews.context).load(it)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivNews)
            }
        }


    }

    fun getItems(): ArrayList<NewsResponse?> {
        return menuItems
    }

    fun remove(index: Int) {
        menuItems.removeAt(index)
    }

    fun addAll(categories: ArrayList<NewsResponse?>) {
        menuItems.addAll(categories)
    }

    fun add(category: NewsResponse?) {
        menuItems.add(category)
    }

}