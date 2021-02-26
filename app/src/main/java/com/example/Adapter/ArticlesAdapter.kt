package com.example.Adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Model.Articles
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import java.io.Serializable

class ArticlesAdapter(var articles: Array<Articles>) :     RecyclerView.Adapter<ArticlesAdapter.ArticleHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArticleHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_article, p0, false)
        return ArticleHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.description.text = article.description
        holder.source.text = "-${article.source?.name}"
        Picasso.get().load(article.urlToImage).placeholder(R.drawable.ic_plaveholder).into(holder.preview)

    }

    inner class ArticleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.title)
        var description = itemView.findViewById<TextView>(R.id.descriptions)
        var source = itemView.findViewById<TextView>(R.id.source)
        var preview = itemView.findViewById<ImageView>(R.id.preview)
    }
}