package com.saba.sampleMVVM.domain.providers.local.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saba.sampleMVVM.domain.models.RepoModel

@Dao
interface RepoDao : BaseDao<RepoModel> {

    @Query("Select * From repos")
    fun select(): LiveData<List<RepoModel>>

}
