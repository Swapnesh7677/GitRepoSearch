package com.swapnesh.gitsearch.retrofit
import com.swapnesh.gitsearch.model.GItRepoResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface JfClient {


//    @FormUrlEncoded
//    @GET("/businesses/search")
//    fun getRest(@FieldMap prams: HashMap<String, String>): Call<RestaurantsResponce>


    @GET("search/repositories")
    fun getRest(@Query ("q")q:String,
                @Query ("sort")sort:String,
                @Query ("star")star:String,
                @Query ("order")order:String,
                @Query ("per_page")per_page:String,
                @Query ("page")page:Int,): Call<GItRepoResponse>


}