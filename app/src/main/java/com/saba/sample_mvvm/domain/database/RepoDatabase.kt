package com.saba.sample_mvvm.domain.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.saba.sample_mvvm.domain.database.daos.RepoDao
import com.saba.sample_mvvm.domain.database.daos.OwnerDao
import com.saba.sample_mvvm.domain.dataModels.dbModels.OwnerDbModel
import com.saba.sample_mvvm.domain.dataModels.dbModels.RepoDbModel

@Database(entities = [RepoDbModel::class, OwnerDbModel::class],
        version = 1,
        exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    abstract fun ownerDao(): OwnerDao

}
