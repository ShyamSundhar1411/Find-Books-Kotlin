package com.shyam.searchyt

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import retrofit2.Callback
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var searchTerm = intent.getStringExtra("Searched_Term").toString()
        val retriever = BookRetriever()
        if (searchTerm != null){
            val callback = object:Callback<BookSearchResult>{
                override fun onResponse(
                    call: Call<BookSearchResult>,
                    response: Response<BookSearchResult>
                ) {
                    val searchedBook = response?.body()
                    if(searchedBook != null){
                        listBooks(searchedBook!!.items)
                    }
                }

                override fun onFailure(call: Call<BookSearchResult>, t: Throwable) {
                    println("Not Working")
                }

            }
            retriever.getBooks(callback,searchTerm)
        }
    }
    fun listBooks(books: List<Items>){
        val listView = findViewById<ListView>(R.id.BookListView)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val selected = books!![i]
            val url = Uri.parse(selected?.volumeInfo?.previewLink)
            val finishIntent = Intent(Intent.ACTION_VIEW,url)
            startActivity(finishIntent)
        }
        val adapter = BookAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,books!!)
        listView.adapter = adapter
    }
    class BookAdapter(context: Context,resource: Int,objects: List<Items>) : ArrayAdapter<Items>(context, resource, objects){
        override fun getCount(): Int {
            return super.getCount()
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val bookView = inflator.inflate(R.layout.custom_video_layout,parent,false)
            val textView = bookView.findViewById<TextView>(R.id.titleView)
            val imageView = bookView.findViewById<ImageView>(R.id.thumbnailView)
            val book = getItem(position)
            var title = book?.volumeInfo?.title
            val author = book?.volumeInfo?.publisher
            if (author != null){
                title = title + '-'+author
            }
            val url = book?.volumeInfo?.imageLinks?.thumbnail
            if (url != null){
                Picasso.with(context).load(Uri.parse(url)).into(imageView)
            }
            textView.text = title
            return bookView
        }
    }
}