package com.yandex.android.mynotesandroid.di

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import com.yandex.android.mynotesandroid.data.locale.LocalRepositoryImpl
import com.yandex.android.mynotesandroid.data.locale.NotesDao
import com.yandex.android.mynotesandroid.data.locale.NotesDatabase
import com.yandex.android.mynotesandroid.data.remote.NotesService
import com.yandex.android.mynotesandroid.data.remote.RemoteRepositoryImpl
import com.yandex.android.mynotesandroid.domain.LoadNotesUseCase
import com.yandex.android.mynotesandroid.domain.LocalRepository
import com.yandex.android.mynotesandroid.domain.RemoteRepository
import com.yandex.android.mynotesandroid.service.AuthService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val appContext : Context) {

    @Provides
    fun context() : Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideNotesDatabase() : NotesDatabase {
        return Room.databaseBuilder(
                appContext,
                NotesDatabase::class.java,
                NotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNotesDao(notesDatabase : NotesDatabase) : NotesDao {
        return notesDatabase.notesDoa()
    }

    @Provides
    @Singleton
    fun provideLoadNotesUseCase(localRepository: LocalRepository,
                                remoteRepository: RemoteRepository) : LoadNotesUseCase {
        return LoadNotesUseCase(localRepository, remoteRepository)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(notesDao: NotesDao) : LocalRepository {
        return LocalRepositoryImpl(notesDao)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(notesService: NotesService, authService: AuthService) : RemoteRepository {
        return RemoteRepositoryImpl(notesService, authService)
    }

    @Provides
    @Singleton
    fun provideNotesService() : NotesService {
        val interceptor = Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .method(original.method(), original.body())
                    .build()

            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        return Retrofit.Builder()
                .baseUrl(NotesService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NotesService::class.java)

    }

    @Provides
    @Singleton
    fun provideAuthService() : AuthService {
        return AuthService(appContext)
    }

}