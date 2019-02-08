package com.saba.sample_mvvm.domain.dataModels.dbModels

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "repository")
data class RepoDbModel(@PrimaryKey val id: Int,
                       val name: String,
                       val language: String,
                       val starCount: Int,
                       val userId: Int)