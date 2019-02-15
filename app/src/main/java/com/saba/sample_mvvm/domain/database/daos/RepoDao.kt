package com.saba.sample_mvvm.domain.database.daos

import android.arch.persistence.room.*
import com.saba.sample_mvvm.domain.dataModels.dbModels.RepoDbModel
import io.reactivex.Flowable

@Dao
interface RepoDao : BaseDao<RepoDbModel> {

    @Query("Select * From repository")
    fun select(): Flowable<List<RepoDbModel>>

}
