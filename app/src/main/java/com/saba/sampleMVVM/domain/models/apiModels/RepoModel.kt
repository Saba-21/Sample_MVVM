package com.saba.sampleMVVM.domain.models.apiModels

import com.google.gson.annotations.SerializedName

data class RepoModel(val id: Int,
                     val name: String,
                     val language: String,
                     @SerializedName("stargazers_count")
                     val starCount: Int,
                     val owner: OwnerModel
)
