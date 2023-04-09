package com.swapnesh.gitsearch.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.swapnesh.gitsearch.model.GItRepoResponse


import com.swapnesh.gitsearch.repo.MainRepo


class MainViewModel(application: Application) : BaseViewModel(application) {

    private val mlrepos: MutableLiveData<GItRepoResponse> = MutableLiveData()

    fun getRepos(q: String, sort: String, star: String, order:String,page:Int): MutableLiveData<GItRepoResponse> {
        return MainRepo.getRest(mlrepos,q, sort, star, order,page)
    }




}