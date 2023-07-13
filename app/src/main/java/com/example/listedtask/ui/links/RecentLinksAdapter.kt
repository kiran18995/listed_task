package com.example.listedtask.ui.links

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listedtask.R
import com.example.listedtask.models.RecentLinks
import java.time.format.DateTimeFormatter

private const val DELIMITER = "T"
private const val DATE_PATTERN = "yyyy-MM-dd"
private const val DEFAULT_DATE = "2022-08-09"
private const val DATE_OF_PATTERN = "dd MMM yyyy"

class RecentLinksAdapter(
    private val context: Context,
    private val RecentLinksList: List<RecentLinks>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<RecentLinksAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(smartLink: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_link_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return RecentLinksList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(RecentLinksList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logo = itemView.findViewById<ImageView>(R.id.logo)
        private val linkName = itemView.findViewById<TextView>(R.id.link_name)
        private val linkDate = itemView.findViewById<TextView>(R.id.link_date)
        private val noClicks = itemView.findViewById<TextView>(R.id.no_clicks)
        private val linkUrl = itemView.findViewById<TextView>(R.id.link_url)
        private val copyLink = itemView.findViewById<ImageView>(R.id.copy_to_clipboard)

        fun bind(recentLinksList: RecentLinks) {
            Glide.with(context).load(recentLinksList.originalImage).into(logo)
            linkName.text = recentLinksList.title
            linkDate.text = filterDate(recentLinksList.createdAt)
            noClicks.text = recentLinksList.totalClicks.toString()
            linkUrl.text = recentLinksList.smartLink
            copyLink.setOnClickListener {
                itemClickListener.onItemClick(recentLinksList.smartLink)
            }
        }

        private fun filterDate(timestamp: String?): String {
            val dateTime = timestamp?.split(DELIMITER)?.toTypedArray()
            val format = DateTimeFormatter.ofPattern(DATE_PATTERN)
            val date = format.parse(dateTime?.get(0) ?: DEFAULT_DATE)
            return DateTimeFormatter.ofPattern(DATE_OF_PATTERN).format(date)
        }
    }
}