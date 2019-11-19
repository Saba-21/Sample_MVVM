package com.saba.sampleMVVM.domain.models

import com.google.gson.annotations.SerializedName

data class OwnerModel(val id: Int,
                      val login:String,
                      @SerializedName("avatar_url")
                      val avatarUrl: String)
