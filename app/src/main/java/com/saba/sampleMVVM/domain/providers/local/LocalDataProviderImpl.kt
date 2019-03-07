package com.saba.sampleMVVM.domain.providers.local

import com.saba.sampleMVVM.domain.database.RepoDatabase
import com.saba.sampleMVVM.domain.models.apiModels.RepoModel
import com.saba.sampleMVVM.custom.helper.Converter
import com.saba.sampleMVVM.domain.models.dbModels.UserWithRepos
import io.reactivex.Observable

class LocalDataProviderImpl(private val repoDb: RepoDatabase): LocalDataProvider {

    override fun select():
            Observable<List<RepoModel>> = repoDb.repoDao().select()
            .map { Converter().convertList(it, repoDb.ownerDao().select()) }
            .toObservable()

    override fun drop(repoModel: RepoModel):
            Observable<RepoModel> = Observable.fromCallable{ dropData(repoModel) }

    override fun save(repoModel: RepoModel):
            Observable<RepoModel> = Observable.fromCallable{ saveData(repoModel) }

    private fun dropData(repoModel: RepoModel): RepoModel {
        repoDb.repoDao().delete(Converter().toRepoDbModel(repoModel))
        repoDb.ownerDao().delete(Converter().toOwnerDbModel(repoModel))
        return repoModel
    }

    private fun saveData(repoModel: RepoModel): RepoModel {
        repoDb.ownerDao().save(Converter().toOwnerDbModel(repoModel))
        repoDb.repoDao().save(Converter().toRepoDbModel(repoModel))
        return repoModel
    }

    override fun selectOwnerWithRepos():
            Observable<List<UserWithRepos>> = repoDb.ownerDao().selectOwnerWithRepos().toObservable()

}
