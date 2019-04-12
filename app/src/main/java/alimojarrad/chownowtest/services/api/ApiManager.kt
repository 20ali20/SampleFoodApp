package alimojarrad.chownowtest.services.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * Created by amojarrad on 1/29/18.
 */


enum class ApiContentType(var contentType: String) {
    JSON("application/json"),
}

class ApiManager(endPoint: String,  var contentType: ApiContentType? = ApiContentType.JSON) {


    private val retrofit: Retrofit

    init {


        val clientBuilder = OkHttpClient.Builder().addInterceptor { chain ->
            var requestBuilder = chain.request().newBuilder()



                when (contentType) {
                    ApiContentType.JSON -> {
                        requestBuilder = chain.request().newBuilder()
                                .addHeader("Content-Type", contentType!!.contentType)
                                .addHeader("Accept", "application/json")
                    }

                }
            val request = requestBuilder.build()

            var response: Response = Response.Builder()
                    .request(request)
                    .code(404)
                    .protocol(Protocol.HTTP_2)
                    .message("")
                    .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                    .build()

            try {
                response = chain.proceed(request)
            } catch (e: ConnectException) {
                Timber.e(e)
            } catch (e: SocketTimeoutException) {
                response = Response.Builder()
                        .request(request)
                        .code(504)
                        .protocol(Protocol.HTTP_2)
                        .message("We're taking longer than usual. Please come back in a couple minutes. If you still don't see an update, try again.")
                        .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                        .build()
            } catch (e: IOException) {
                Timber.e(e)
            }
            response
        }

        val client = when (contentType) {
            ApiContentType.JSON -> {
                clientBuilder
                        .connectTimeout(1,TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
            }
            else -> {
                clientBuilder
            }
        }
                .addNetworkInterceptor(StethoInterceptor())
                .build()


        val gson = GsonBuilder()
                .create()
        retrofit = Retrofit.Builder()
                .baseUrl(endPoint)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    fun <T> createAPI(apiInterface: Class<T>): T {
        return retrofit.create(apiInterface)
    }




}



