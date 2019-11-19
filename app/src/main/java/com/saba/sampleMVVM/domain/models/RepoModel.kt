package com.saba.sampleMVVM.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repos")
data class RepoModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val language: String,
    @SerializedName("stargazers_count")
    val starCount: Int,
    val owner: OwnerModel
)
