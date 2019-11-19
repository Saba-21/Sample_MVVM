package com.saba.sampleMVVM.domain.providers.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saba.sampleMVVM.domain.models.OwnerModel

class RepoConverter {
    var gson = Gson()

    @TypeConverter
    fun objectToJson(data: OwnerModel?): String? {
        if (data == null)
            return null
        return gson.toJson(data)
    }

    @TypeConverter
    fun jsonToObject(json: String?): OwnerModel? {
        if (json == null)
            return null
        val type = object : TypeToken<OwnerModel>() {}.type
        return gson.fromJson(json, type)
    }

}