package com.saba.sampleMVVM.domain.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.saba.sampleMVVM.domain.database.daos.RepoDao
import com.saba.sampleMVVM.domain.database.daos.OwnerDao
import com.saba.sampleMVVM.domain.models.dbModels.OwnerDbModel
import com.saba.sampleMVVM.domain.models.dbModels.RepoDbModel

@Database(entities = [RepoDbModel::class, OwnerDbModel::class],
        version = 1,
        exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    abstract fun ownerDao(): OwnerDao

}
