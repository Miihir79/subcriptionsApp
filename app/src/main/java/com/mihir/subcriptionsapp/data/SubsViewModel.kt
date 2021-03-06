package com.mihir.subcriptionsapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public class SubsViewModel(application: Application):AndroidViewModel(application) {

    val readAllData: LiveData<List<Subscription>>
    private val repository: SubsRepo

    init {
        val dao = SubsDatabase.getDatabase(application).subsDao()
        repository = SubsRepo(dao)
        readAllData = repository.readAllData

    }

    fun addSubs(subs: Subscription){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSubs(subs)
        }

    }

    fun updateSubs(subs: Subscription){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSub(subs)
        }
    }

    fun deleteSubs(subs: Subscription){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteSub(subs)
        }
    }
}