package com.saba.sampleMVVM.domain.providers.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saba.sampleMVVM.domain.models.RepoModel
import com.saba.sampleMVVM.domain.providers.local.converters.RepoConverter
import com.saba.sampleMVVM.domain.providers.local.daos.RepoDao

@Database(
    entities = [RepoModel::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(RepoConverter::class)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

}