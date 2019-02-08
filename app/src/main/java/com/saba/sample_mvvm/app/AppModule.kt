package com.saba.sample_mvvm.app

import android.arch.persistence.room.Room
import androidx.navigation.Navigation
import com.google.gson.GsonBuilder
import com.saba.sample_mvvm.R
import com.saba.sample_mvvm.custom.ACTIVITY
import com.saba.sample_mvvm.domain.dataProviders.global.GlobalDataProvider
import com.saba.sample_mvvm.domain.dataProviders.local.LocalDataProvider
import com.saba.sample_mvvm.domain.dataProviders.local.LocalDataProviderImpl
import com.saba.sample_mvvm.domain.database.RepoDatabase
import com.saba.sample_mvvm.domain.repository.Repository
import com.saba.sample_mvvm.domain.repository.RepositoryImpl
import com.saba.sample_mvvm.domain.useCases.DropLocalReposUseCase
import com.saba.sample_mvvm.domain.useCases.GetGlobalReposUseCase
import com.saba.sample_mvvm.domain.useCases.GetLocalReposUseCase
import com.saba.sample_mvvm.domain.useCases.SaveLocalRepoUseCase
import com.saba.sample_mvvm.presentation.add.AddingViewModel
import com.saba.sample_mvvm.presentation.get.ResultViewModel
import com.saba.sample_mvvm.presentation.main.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    val appModule = module {

        single {
            GsonBuilder().create()
        }
        single {
            GsonConverterFactory.create(get()) as Converter.Factory
        }
        single {
            RxJava2CallAdapterFactory.create() as CallAdapter.Factory
        }
        single {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) as Interceptor
        }
        single {
            OkHttpClient.Builder().addInterceptor(get()).build()
        }
        single {
            Retrofit
                .Builder()
                .baseUrl(androidContext().getString(R.string.base_url))
                .client(get())
                .addConverterFactory(get())
                .addCallAdapterFactory(get())
                .build()
        }
        single {
            Room
                .databaseBuilder(
                    androidApplication(),
                    RepoDatabase::class.java,
                    androidContext().getString(R.string.db_name)
                )
                .fallbackToDestructiveMigration()
                .build()
        }
        single {
            get<Retrofit>().create(GlobalDataProvider::class.java)
        }
        single {
            LocalDataProviderImpl(get()) as LocalDataProvider
        }
        single {
            RepositoryImpl(get(), get()) as Repository
        }
        factory {
            DropLocalReposUseCase(get())
        }
        factory {
            GetGlobalReposUseCase(get())
        }
        factory {
            GetLocalReposUseCase(get())
        }
        factory {
            SaveLocalRepoUseCase(get())
        }

        viewModel<AddingViewModel>()

        viewModel<ResultViewModel>()

        viewModel<MainViewModel>()

        scope(ACTIVITY) {
            Navigation.findNavController(it[0], R.id.mainNavHost)
        }

    }

}