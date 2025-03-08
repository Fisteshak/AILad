package com.example.ailad.di

import android.content.Context
import androidx.room.Room
import com.example.ailad.data.api.LlamaApi
import com.example.ailad.data.api.RTULabApi
import com.example.ailad.data.db.AppDatabase
import com.example.ailad.data.db.MessageDao
import com.example.ailad.data.db.RAGDao
import com.example.ailad.data.repositories.AnswerNetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RTULabRetrofitClient {
    private const val RTU_LAB_URL = "http://94.126.205.209:8000/"

    private var client = OkHttpClient.Builder()
        .connectTimeout(3, TimeUnit.MINUTES)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()


    private fun createRetrofit(url: String): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val instance = createRetrofit(RTU_LAB_URL)

    private val apiService: RTULabApi = instance.create(RTULabApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofitApi(): RTULabApi {
        return apiService
    }
}


@Module
@InstallIn(SingletonComponent::class)
object LlamaRetrofitClient {
    private const val LLAMA_URL = "https://api.hyperbolic.xyz/v1/chat/completions"

    private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbnRvbmxhemFyZXYyMDA1QGdtYWlsLmNvbSIsImlhdCI6MTczOTI3NzQ0Mn0.WEGghvPDakHHt1ZADrXbYz5CTFZbpMz4yjymWudLqXA"

    private var client: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()
        chain.proceed(newRequest)
    }.build()


    private fun createRetrofit(url: String): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val instance = createRetrofit(LLAMA_URL)

    private val apiService: LlamaApi = instance.create(LlamaApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofitApi(): LlamaApi {
        return apiService
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNetworkCurrenciesRepository(api: RTULabApi) : AnswerNetworkRepository {
        return AnswerNetworkRepository(api)
    }


}

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Volatile
    private var Instance: AppDatabase? = null

    @Provides
    @Singleton
    fun currencyDatabase(@ApplicationContext context: Context): AppDatabase {
        // if the Instance is not null, return it, otherwise create a new database instance.
        return Instance ?: synchronized(this) {
            Room.databaseBuilder(context, AppDatabase::class.java, "main_database")
                .build()
                .also { Instance = it }
        }
    }

    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase): MessageDao {
        return database.messageDao()
    }

    @Provides
    @Singleton
    fun provideRAGDao(database: AppDatabase): RAGDao {
        return database.RAGDao()
    }

}
