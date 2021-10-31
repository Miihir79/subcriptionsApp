package com.mihir.subcriptionsapp.data

import androidx.lifecycle.LiveData

class SubsRepo(private val dao: Subs_dao) {

    val readAllData: LiveData<List<Subscription>> = dao.readAllSubs()

    suspend fun addSubs(subs: Subscription){

        dao.addSubs(subs)
    }
    suspend fun updateSub(subs: Subscription){
        dao.updateSubs(subs)
    }
   suspend fun deleteSub(subs: Subscription){
        dao.deleteSubs(subs)
    }
}