package com.saba.sample_mvvm.domain.database.daos

import android.arch.persistence.room.*
import com.saba.sample_mvvm.domain.dataModels.dbModels.OwnerDbModel
import com.saba.sample_mvvm.domain.dataModels.dbModels.UserWithRepos
import io.reactivex.Flowable

@Dao
interface OwnerDao : BaseDao<OwnerDbModel> {

    @Query("Select * From owner")
    fun select(): List<OwnerDbModel>

    @Query("Select * From owner")
    fun selectOwnerWithRepos(): Flowable<List<UserWithRepos>>

}
