package com.saba.sampleMVVM.app.modules

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.saba.sampleMVVM.R
import com.saba.sampleMVVM.domain.providers.global.GlobalDataProvider
import com.saba.sampleMVVM.domain.providers.local.RepoDatabase
import com.saba.sampleMVVM.domain.repository.Repository
import com.saba.sampleMVVM.domain.repository.RepositoryImpl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
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
            OkHttpClient
                .Builder()
                .build()
        }
        single {
            Retrofit
                .Builder()
                .baseUrl(androidContext().getString(R.string.base_url))
                .client(get())
                .addConverterFactory(get())
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
            get<Retrofit>()
                .create(GlobalDataProvider::class.java)
        }

        single {
            RepositoryImpl(get(), get()) as Repository
        }

    }

}