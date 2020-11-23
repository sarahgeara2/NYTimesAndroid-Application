package com.example.nytimesapplication.core.di.modules



import com.example.nytimesapplication.App
import com.example.nytimesapplication.BuildConfig
import com.example.nytimesapplication.common.GlobalVars
import com.example.nytimesapplication.core.RestService
import com.example.nytimesapplication.core.util.RetryCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRestService(app: App): RestService {

        val okHttpClientBuilder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        if (BuildConfig.DEBUG)
            okHttpClientBuilder.addInterceptor(logging)


        return Retrofit.Builder()
            .baseUrl(GlobalVars.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(RetryCallAdapterFactory.create(app.mActivity))
            .build()
            .create(RestService::class.java)

    }

}
