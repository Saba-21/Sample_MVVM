package com.saba.sampleMVVM.domain.providers.local.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg item: T)

    @Delete
    suspend fun delete(vararg item: T)

}