package io.strelnikov.lab3

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var list: ArrayList<News> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val description: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.description.text = list[position].description

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsActivity::class.java)
            intent.putExtra("title", list[position].title)
            intent.putExtra("date", list[position].pubDate)
            intent.putExtra("content", list[position].content)
            intent.putExtra("countries", list[position].country)
            intent.putExtra("link", list[position].link)
            intent.putExtra("categories", list[position].category)
            intent.putExtra("authors", list[position].creator)
            holder.itemView.context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItemList: ArrayList<News>) {
        list = newItemList
        notifyDataSetChanged()
    }
}