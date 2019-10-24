package com.saba.sampleMVVM.app.modules

import android.arch.persistence.room.Room
import androidx.navigation.Navigation
import com.google.gson.GsonBuilder
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.base.presentation.ACTIVITY
import com.saba.sampleMVVM.domain.providers.global.GlobalDataProvider
import com.saba.sampleMVVM.domain.providers.local.LocalDataProvider
import com.saba.sampleMVVM.domain.providers.local.LocalDataProviderImpl
import com.saba.sampleMVVM.domain.database.RepoDatabase
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.domain.repository.RepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    val module = module {

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
            OkHttpClient.Builder()
                    .addInterceptor(get())
                    .build()
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

        scope(ACTIVITY) {
            Navigation.findNavController(it[0], R.id.mainNavHost)
        }

    }

}