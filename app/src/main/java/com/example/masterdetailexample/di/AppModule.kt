package com.example.masterdetailexample.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.masterdetailexample.common.Constants
import com.example.masterdetailexample.data.local.AppDataBase
import com.example.masterdetailexample.data.remote.api.MasterDetailApi
import com.example.masterdetailexample.domain.repositories.FavoritesRepository
import com.fasterxml.jackson.module.kotlin.SingletonSupport
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build()

    @Singleton
    @Provides
    fun provideRuta99Api(okHttpClient: OkHttpClient) : MasterDetailApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(MasterDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context) : AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        Constants.NAME_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideFavoritesRepository(db: AppDataBase): FavoritesRepository {
        return FavoritesRepository(db.favoritesDao())
    }
}