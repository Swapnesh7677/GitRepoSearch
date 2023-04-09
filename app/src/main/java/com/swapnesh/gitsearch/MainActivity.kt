package com.swapnesh.gitsearch

import android.R
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swapnesh.gitsearch.databinding.ActivityMainBinding
import com.swapnesh.gitsearch.model.GItRepoResponse
import com.swapnesh.gitsearch.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVm: MainViewModel
    private val gitData: ArrayList<GItRepoResponse.Item> = ArrayList()
    var pagecount :Int = 1
    var value : String= "Best match"
    var numberOFProductsFetched = 0
    private var totalNumber = 0
    private var isFetchIngPageData = false
    private var gitAdapter: GitAdapter? = null
    var layoutManager: LinearLayoutManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainVm = ViewModelProvider.AndroidViewModelFactory(application)
            .create(MainViewModel::class.java)
        layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvGit.setHasFixedSize(true)
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        binding.rvGit.layoutManager = layoutManager

        val list = ArrayList<String>()
        list.add("Best match")
        list.add("Most stars")
        list.add("Fewest star")
        list.add("Most forks")
        list.add("Fewest forks")

        val adapter: ArrayAdapter<*> =
            ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, list)
        binding.sortspinner.adapter = adapter
        binding.sortspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
                value = adapterView.getItemAtPosition(i).toString()

                if(!value.equals("Best match")){
                    getData(value,pagecount)
                }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.searchBtn.setOnClickListener {
            gitData.clear()
            getData(value, pagecount)
            hideKeyboard(binding.searchBtn)

        }
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.length==0){
                    gitData.clear()
                    getData(value, pagecount)

                    if (gitAdapter == null) {
                        gitData.clear()
                        gitAdapter = GitAdapter(gitData)
                        binding.rvGit.setAdapter(gitAdapter)
                    } else {
                        gitAdapter!!.notifyDataSetChanged()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })



        binding.rvGit.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCurrentlyInRv: Int
                val lastVisible: Int
                totalItemCurrentlyInRv = layoutManager!!.getItemCount()
                lastVisible = layoutManager!!.findLastVisibleItemPosition()
                val endHasBeenReachedToBottomOfRv = lastVisible + 1 >= totalItemCurrentlyInRv
                if (totalNumber > numberOFProductsFetched) {
                    if (totalItemCurrentlyInRv > 0 && endHasBeenReachedToBottomOfRv) {
                        if (!isFetchIngPageData) {
                            isFetchIngPageData = true
                            pagecount++
                            getData(value, pagecount)
                        }
                    }
                }
            }
        })

    }

    private  fun getData(valuet: String, pagecountt: Int) {
        binding.progressLoader.visibility = View.VISIBLE
        mainVm.getRepos( binding.editSearch.text.toString(), valuet,"","asc", pagecountt).observe(this@MainActivity) {
            if(it!=null){

                setdata(it)
            }
        }
    }

    private fun setdata(it: GItRepoResponse) {
        totalNumber = it.total_count
        gitData.addAll(it.items)
        if (gitAdapter == null) {
            gitAdapter = GitAdapter(gitData)
            binding.rvGit.setAdapter(gitAdapter)
        } else {
            gitAdapter!!.notifyDataSetChanged()
        }
        binding.progressLoader.visibility = View.GONE
        numberOFProductsFetched = gitAdapter!!.getItemCount()
        isFetchIngPageData = false
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}