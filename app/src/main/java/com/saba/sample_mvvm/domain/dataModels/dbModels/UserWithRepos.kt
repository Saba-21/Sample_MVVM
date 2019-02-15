package com.saba.sample_mvvm.domain.dataModels.dbModels

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class UserWithRepos {

    @Embedded
    lateinit var owner: OwnerDbModel

    @Relation(parentColumn = "id", entityColumn = "userId")
    lateinit var repoList: List<RepoDbModel>

}