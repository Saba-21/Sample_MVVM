package com.saba.sampleMVVM.domain.database.daos

import android.arch.persistence.room.*
import com.saba.sampleMVVM.domain.models.dbModels.OwnerDbModel
import com.saba.sampleMVVM.domain.models.dbModels.UserWithRepos
import io.reactivex.Flowable

@Dao
interface OwnerDao : BaseDao<OwnerDbModel> {

    @Query("Select * From owner")
    fun select(): List<OwnerDbModel>

    @Query("Select * From owner")
    fun selectOwnerWithRepos(): Flowable<List<UserWithRepos>>

}
