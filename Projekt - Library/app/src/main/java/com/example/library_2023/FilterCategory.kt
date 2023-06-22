package com.example.library_2023

import android.content.Context
import java.nio.file.FileVisitResult

class FilterCategory {
    private val adapterCategory: adapterCategory
    private val filterList: ArrayList<ModelCategory>

    constructor(adapterCategory: Any, filterList: ArrayList<ModelCategory>):super() {
        this.adapterCategory = adapterCategory
        this.filterList = filterList
    }

    override fun performFiltering(constsraint:CharSequence?): FilterResults{
        var constsraint = constsraint
        val results = FilterResults()

        if(constsraint != null && constsraint.isNotEmpty()){
            constsraint = constsraint.toString().uppercase()
            val filterModel:ArrayList<ModelCategory> = ArrayList()
            for(i in 0 until filterList.size){
                filterModel.add(filterList[i])
            }
        }
        results.count = filteredModels.size
        results.values = filteredModels
    }else{
        results.count = filterList.size
        results.values = filterList
    }

    override fun publishResults(constsraint: CharSequence?, result: FilterResults?){
        adapterCategory.categoryArrayList = results.values as ArrayList<ModelCategory>

        adapterCategory.notifyDataSetChanged()
    }
}