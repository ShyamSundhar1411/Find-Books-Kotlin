package com.shyam.searchyt

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val API_KEY = "AIzaSyC4lomMLWKw8OGVOBfSr9RsxtznY__TlCs"
interface YouTubeService{
    @GET("youtube/v3/search?")
    fun searchVideos(@Query("q") q:String,@Query("key") key:String) : Call<YouTubeSearchResult>
}
class YouTubeSearchResult(val items : List<Videos>)
class Videos(val id : String , val resource : List<Snippet>)
class Snippet(val title : String,val thumbnail : String)

class VideoRetriever{
    val service : YouTubeService
    init {
        val retrofit =
            Retrofit.Builder().baseUrl("https://https://www.googleapis.com/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        service = retrofit.create(YouTubeService::class.java)
    }
    fun getVideos(callBack : Callback<YouTubeSearchResult>,searchTerm:String){
        var searchT = searchTerm
        if(searchT == ""){
            searchT = "Kotlin"
        }
        val call = service.searchVideos(searchT, API_KEY)
        call.enqueue(callBack)
    }
}