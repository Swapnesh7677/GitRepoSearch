package com.swapnesh.gitsearch.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.swapnesh.gitsearch.model.GItRepoResponse
import com.swapnesh.gitsearch.utils.VRC
import com.swapnesh.gitsearch.retrofit.JfClient
import com.swapnesh.gitsearch.retrofit.JfServer
import com.swapnesh.gitsearch.util.jflibs.Logger
import com.swapnesh.gitsearch.utils.Constants.Companion.API_ERROR_TAG
import com.swapnesh.gitsearch.utils.Constants.Companion.API_INVALID_RESPONSE_ERROR
import com.swapnesh.gitsearch.utils.Constants.Companion.API_NULL_RESPONSE_ERROR
import com.swapnesh.gitsearch.utils.Constants.Companion.API_URL_TAG


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object MainRepo {
    private var jfClient: JfClient = JfServer.getJfServer().create(JfClient::class.java)


    fun getRest(ml_restaurant: MutableLiveData<GItRepoResponse>, q: String, sort: String, star: String, order:String,page:Int): MutableLiveData<GItRepoResponse> {
        val apiName = "search/repositories"
        val params = HashMap<String, String>()

        Log.d("responce",q)
        val req: Call<GItRepoResponse> = jfClient.getRest(q,sort,star,order,"20",page)
        Logger.logApi(
            API_URL_TAG,
            apiName,
            VRC(req.request().url.toString(),params).getRequestedUrl()
        )

        req.enqueue(object : Callback<GItRepoResponse> {
            override fun onResponse(call: Call<GItRepoResponse>, response: Response<GItRepoResponse>) {
                if (response.isSuccessful) {

                    if (response.body() != null) {
                        val  gitResponse = response.body()!!
                        Log.d("responce",gitResponse.toString())
                        ml_restaurant.value = gitResponse
                    } else {

                        Logger.logApi(API_ERROR_TAG, apiName, API_NULL_RESPONSE_ERROR)
                        ml_restaurant.value = null
                    }

                } else {

                    Logger.logApi(API_ERROR_TAG, apiName, API_INVALID_RESPONSE_ERROR)
                    ml_restaurant.value = null
                }
            }


            override fun onFailure(call: Call<GItRepoResponse>, t: Throwable) {
                t.printStackTrace()
                Logger.logApi(API_ERROR_TAG, apiName, t.localizedMessage)
                ml_restaurant.value =null
            }

        })
        return ml_restaurant
    }



}