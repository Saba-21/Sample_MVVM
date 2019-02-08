package com.saba.sample_mvvm.domain.database.daos

import android.arch.persistence.room.*
import com.saba.sample_mvvm.domain.dataModels.dbModels.OwnerDbModel

@Dao
interface OwnerDao {

    @Query("Select * From owner")
    fun select(): List<OwnerDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(owner: OwnerDbModel)

    @Delete
    fun drop(owner: OwnerDbModel)

}
