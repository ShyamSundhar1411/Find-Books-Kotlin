package com.shyam.searchyt

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService{
    @GET("books/v1/volumes?")
    fun searchBooks(@Query("q") q:String) : Call<BookSearchResult>
}
class BookSearchResult(val items : List<Items>)
class Items(val id : String , val volumeInfo : VolumeInfo)
class VolumeInfo(val title : String,val previewLink : String,val imageLinks : Thumbnail, val publisher : String)
class Thumbnail(val thumbnail : String)
class BookRetriever{
    val service : BookService
    init {
        val retrofit =
            Retrofit.Builder().baseUrl("https://www.googleapis.com/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        service = retrofit.create(BookService::class.java)
    }
    fun getBooks(callBack : Callback<BookSearchResult>,searchTerm:String){
        var searchT = searchTerm
        if(searchT == ""){
            searchT = "Kotlin"
        }
        val call = service.searchBooks(searchT)
        call.enqueue(callBack)
    }
}