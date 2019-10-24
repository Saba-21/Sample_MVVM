package com.saba.sampleMVVM.domain.database.daos

import android.arch.persistence.room.*
import com.saba.sampleMVVM.domain.models.dbModels.OwnerDbModel

@Dao
interface OwnerDao : BaseDao<OwnerDbModel> {

    @Query("Select * From owner")
    fun select(): List<OwnerDbModel>

}
