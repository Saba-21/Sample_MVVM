package com.saba.sample_mvvm.domain.database.daos

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg item: T)

    @Delete
    fun delete(vararg item: T)

}