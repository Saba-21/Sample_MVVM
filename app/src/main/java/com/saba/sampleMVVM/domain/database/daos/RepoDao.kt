package com.saba.sampleMVVM.domain.database.daos

import android.arch.persistence.room.*
import com.saba.sampleMVVM.domain.models.dbModels.RepoDbModel
import io.reactivex.Flowable

@Dao
interface RepoDao : BaseDao<RepoDbModel> {

    @Query("Select * From repository")
    fun select(): Flowable<List<RepoDbModel>>

}
